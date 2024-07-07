
使用 scrapy, scrapy-redis, graphite 实现的京东分布式爬虫，以 mongodb 实现底层存储。分布式
实现，解决带宽和性能的瓶颈，提高爬取的效率。实现 scrapy-redis 对进行 url 的去重
以及调度，利用redis的高效和易于扩展能够轻松实现高效率下载：当redis存储或者访问速
度遇到瓶颈时，可以通过增大redis集群数和爬虫集群数量改善
* 爬取策略
  获取 ~<a href>~ 标签里面的 url 值，然后迭代爬取，并且把 url 限定在
  ~xxx.jd.com~ 范围内，防止无限广度的问题。
* 反反爬虫策略
** 禁用 cookie
   通过禁用 cookie, 服务器就无法根据 cookie 判断出爬虫是否访问过网站
** 伪装成搜索引擎
   现在可以通过修改 user-agent 伪装成搜索引擎
   #+BEGIN_SRC 
    'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)',
    'Mozilla/5.0 (compatible; Bingbot/2.0; +http://www.bing.com/bingbot.htm)',
    'Mozilla/5.0 (compatible; Yahoo! Slurp; http://help.yahoo.com/help/us/ysearch/slurp)',
    'DuckDuckBot/1.0; (+http://duckduckgo.com/duckduckbot.html)',
    'Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)',
    'Mozilla/5.0 (compatible; YandexBot/3.0; +http://yandex.com/bots)',
    'ia_archiver (+http://www.alexa.com/site/help/webmasters; crawler@alexa.com)',
   #+END_SRC
** 轮转 user-agent
   为了提高突破反爬虫策略的成功率，定义多个user-agent, 然后每次请求都随机选择
   user-agent。本爬虫实现了一个 ~RotateUserAgentMiddleware~ 类来实现 user-agent
   的轮转
** 代理 IP
   使用代理 IP, 防止 IP 被封
* 爬虫状态监控
  将爬虫stats信息(请求个数，item下载个数，dropItem个数，日志)保存到redis中
  实现了一个针对分布式的stats collector，并将其结果用graphite以图表形式动态实时显示
* 并发请求和深度控制
  通过 ~setting.py~ 中的 ~CONCURRENT_REQUESTS = 32~ 配置来控制并发请求数量，通过
  ~DepthMiddle~ 类的 ~DEPTH_LIMIT=max~ 参数来控制爬虫的的递归深度
* 项目依赖
  + python 3.5+
  + scrapy
  + scrapy-redis
  + pymongo
  + graphite
* 如何运行
  #+BEGIN_SRC  sh
    git clone  https://github.com/samrayleung/jd_spider.git 
  #+END_SRC
  然后安装 python依赖
  #+BEGIN_SRC sh
    (sudo) pip install -r requirements.txt
  #+END_SRC
** docker 安装
   安装配置 graphite. 需要注意的是 graphite 只适用于 Linux 平台，且安装过程非常
   麻烦，所以强烈建议使用 docker 进行安装。我基于 [[https://github.com/hopsoft/docker-graphite-statsd][docker-graphite-statsd]] 这个
   graphite 的镜像作了些许配置文件的修改，以适配 scrapy. 运行以下命令以拉取并运
   行 image
   #+BEGIN_SRC sh
     sudo docker run -d\
	  --name graphite\
	  --restart=always\
	  -p 80:80\
	  -p 2003-2004:2003-2004\
	  -p 2023-2024:2023-2024\
	  -p 8125:8125/udp\
	  -p 8126:8126\
	  samrayleung/graphite-statsd
   #+END_SRC
   然后就可以在浏览器打开：
   [[http://localhost/dashboard][dashboard]]
   或者是登录到管理界面：
   [[http://localhost/account/login]]
   默认帐号密码是：
   + username: root
   + password: root
** 手动安装
   当然，你也可以自己配置 graphite, 在成功配置 graphite 之后，需要修改一些配置：
   + 把 ~/opt/graphite/webapp/content/js/composer_widgets.js~ 文件中
     ~toggleAutoRefresh~ 函数里的 ~interval~ 变量从60改为1。
   + 在配置文件 ~storage-aggregation.conf~ 里添加：
     #+BEGIN_SRC 
     [scrapy_min]
    pattern = ^scrapy\..*_min$
    xFilesFactor = 0.1
    aggregationMethod = min
    [scrapy_max]
    pattern = ^scrapy\..*_max$
    xFilesFactor = 0.1
    aggregationMethod = max
    [scrapy_sum]
    pattern = ^scrapy\..*_count$
    xFilesFactor = 0.1
    aggregationMethod = sum
     #+END_SRC
     而 ~storage-aggregation.conf~ 这个配置文件一般是位于 ~/opt/graphite/conf~
 
     一切准备就绪之后，就可以运行爬虫了。
     进入到 ~jd~ 目录下：
     #+BEGIN_SRC sh
       scrapy crawl jindong
     #+END_SRC
** 注意事项
   需要注意的是，本项目是含有两只爬虫，爬取商品评论需要先爬取商品信息，因为有了
   商品信息才能爬取评论
** 代理 IP
   虽然不使用代理 IP 可以爬取商品信息，但是可能爬取一段时间后就无法爬取商品信息，
   所以需要添加代理 IP. 以 http://ip:port 的形式保存到文本文件，每行一个 IP,然后
   在 ~setting~ 中指定路径：
   #+BEGIN_SRC python
     PROXY_LIST = 'path/to/proxy_ip.txt'
   #+END_SRC
   并且去掉下面配置的注释：
   #+BEGIN_SRC python
     RETRY_TIMES = 10
     RETRY_HTTP_CODES = [500, 503, 504, 400, 403, 404, 408]

     DOWNLOADER_MIDDLEWARES = {
	 'scrapy.downloadermiddlewares.retry.RetryMiddleware': 90,
	 'scrapy_proxies.RandomProxy': 100,
	 'scrapy.downloadermiddlewares.httpproxy.HttpProxyMiddleware': 110,
     }
     PROXY_MODE = 0
   #+END_SRC
   
* 运行截图
** graphite 监控

   [[./images/jd_comment_graphite1.png]]
   
   [[./images/jd_comment_graphite2.png]]
** 评论
   [[./images/jd_comment.png]]
** 评论总结
   [[./images/jd_comment_summary.png]]
** 商品信息
   [[./images/jd_parameters.png]]

* 参考及致谢
  + [[https://github.com/noplay/scrapy-graphite]]
  + [[https://github.com/gnemoug/distribute_crawler]]
  + https://github.com/hopsoft/docker-graphite-statsd
  + [[https://github.com/aivarsk/scrapy-proxies]]
