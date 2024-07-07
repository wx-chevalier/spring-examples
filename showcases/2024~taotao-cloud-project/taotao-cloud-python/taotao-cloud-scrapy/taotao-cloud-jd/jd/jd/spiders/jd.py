import json
import logging
import random
import uuid
from urllib.parse import urlparse

import scrapy

from jd.items import ParameterItem
from jd.spiders.exception import ParseNotSupportedError

logger = logging.getLogger('jindong')


class JDSpider(scrapy.Spider):
    name = "jindong"
    rotete_user_agent = True

    def __init__(self):
        self.price_url = "http://p.3.cn/prices/mgets?pduid={}&skuIds=J_{}"
        self.price_backup_url = "https://p.3.cn/prices/get?pduid={}&skuid=J_{}"
        self.jd_subdomain = ["jiadian", "shouji", "wt", "shuma", "diannao",
                             "bg", "channel", "jipiao", "hotel", "trip",
                             "ish", "book", "e", "health", "baby", "toy",
                             "nong", "jiu", "fresh", "china", "che", "list"]

    def start_requests(self):
        urls = ['https://jd.com/']
        for url in urls:
            yield scrapy.Request(url=url, callback=self.parse)

    def parse(self, response):
        links = response.xpath("//@href").extract()
        for link in links:
            self.logger.debug("link: %s", link)
            url = urlparse(link)
            if url.netloc:  # 是一个 URL 而不是 "javascript:void(0)"
                if not url.scheme:
                    link = "http:" + link
                self.logger.debug("url: %s", url)
                subdomain = url.hostname.split(".")[0]
                # 如果这是一个商品，获取商品详情
                if subdomain in ["item"]:
                    yield scrapy.Request(url=link, callback=self.parse_item)
                # 判断是否是 xxx.jd.com, 限制爬取范围在 jd.com
                elif subdomain in self.jd_subdomain:
                    yield scrapy.Request(url=link, callback=self.parse)

    def parse_item(self, response):
        parsed = urlparse(response.url)
        item = ParameterItem()
        # 获取 sku_id, URL: http://item.jd.com/4297772.html 的 sku_id 就是 4297772
        item['sku_id'] = urlparse(response.url).path.split(".")[
            0].replace("/", "")
        item['brand'] = response.xpath(
            "id('parameter-brand')/li/a[1]/text()").extract()
        item['_id'] = str(uuid.uuid4())
        names = response.xpath('//div[@class="sku-name"]/text()').extract()
        name = ''.join(str(i) for i in names).strip()
        if name == '':
            name = response.xpath("id('name')/h1/text()").extract()
        item['name'] = name
        item['item_name'] = 'parameter'
        details2 = []
        # 不同的子域名对应的页面解析规则不一样
        if parsed.hostname != 'e.jd.com':
            # 通用的解析策略
            details1 = response.xpath(
                '//ul[@class="parameter1 p-parameter-list"]/li/div/p/text()').extract()
            item['parameters1'] = details1
            details2 = response.xpath(
                '//ul[@class="parameter2 p-parameter-list"]/li/text()').extract()
            if details2 == []:
                try:
                    # 解析图书
                    details2 = self.parse_book(response)
                except ParseNotSupportedError as err:
                    # 解析全球购
                    parsed_url = urlparse(response.url)
                    subdomain = parsed_url.hostname
                    if subdomain == "item.jd.hk":
                        logging.info("parse global shopping item, url: {}"
                                     .format(response.url))
                        details2 = self.parse_global_shopping(response)
                    else:
                        # 无法解析
                        referer = response.request.headers.get('Referer', None)
                        logging.warn("Unknow item, could not parse. refrece: {}, url: {}"
                                     .format(referer, response.url))
        item['parameters2'] = details2
        yield scrapy.Request(url=self.price_url.format(random.randint(1, 100000000), item['sku_id']),
                             callback=self.parse_price,
                             meta={'item': item})

    def parse_price(self, response):
        item = response.meta['item']
        try:
            price = json.loads(response.text)
            item['price'] = price[0].get("p")
        except json.decoder.JSONDecodeError as err:
            logging.log('prase price failed, try backup price url')
            yield scrapy.Request(url=self.price_backup_url.format(random.randint(1, 100000000),
                                                                  item['sku_id']),
                                 callback=self.parse_price,
                                 meta={'item': item})
        yield item

    def parse_global_shopping(self, response):
        try:
            details2 = []
            brand = {}
            brand_name = response.xpath(
                "id('item-detail')/div[1]/ul/li[3]/text()").extract()[0]
            brand[brand_name] = response.xpath(
                "id('item-detail')/div[1]/ul/li[3]/a/text()").extract()[0]
            details2.append(brand)
            parameters = response.xpath(
                "id('item-detail')/div[1]/ul/li/text()").extract()
            details2 += parameters[0:2]
            details2 += parameters[3:]
            return details2
        except IndexError as err:
            referer = response.request.headers.get('Referer', None)
            logging.warn("global shopping parses failed. referer: {}, url: {}".format(
                referer, response.url))

    def parse_book(self, response):
        try:
            details2 = []
            shop = {}
            shop_name = response.xpath(
                "id('parameter2')/li[1]/text()").extract()[0]
            shop[shop_name] = response.xpath(
                "id('parameter2')/li[1]/a/text()").extract()[0]
            details2.append(shop)
            publisher = {}
            publisher_name = response.xpath(
                "id('parameter2')/li[2]/text()").extract()[0]
            publisher[publisher_name] = response.xpath(
                "id('parameter2')/li[2]/a/text()").extract()
            details2.append(publisher)
            details2 += response.xpath(
                "id('parameter2')/li/text()").extract()[3:]
            return details2
        except IndexError as err:
            referer = response.request.headers.get('Referer', None)
            logging.info("It's not a book item, parse failed. referer: {}, url: {}"
                         .format(referer, response.url))
            raise ParseNotSupportedError(response.url)
