/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.taotao.cloud.goods.application.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.taotao.cloud.cache.redis.repository.RedisRepository;

import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.goods.application.elasticsearch.entity.EsGoodsIndex;
import com.taotao.cloud.goods.application.elasticsearch.pojo.EsGoodsRelatedInfo;
import com.taotao.cloud.goods.application.service.IEsGoodsIndexService;
import com.taotao.cloud.goods.application.service.IEsGoodsSearchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FieldValueFactorFunctionBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.GaussDecayFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

/**
 * ES商品搜索业务层实现
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-27 17:02:32
 */
@Service
public class EsGoodsSearchServiceImpl implements IEsGoodsSearchService {

    private static final String ATTR_PATH = "attrList";
    private static final String ATTR_VALUE = "attrList.value";
    private static final String ATTR_NAME = "attrList.name";
    private static final String ATTR_SORT = "attrList.sort";
    private static final String ATTR_BRAND_ID = "brandId";
    private static final String ATTR_BRAND_NAME = "brandNameAgg";
    private static final String ATTR_BRAND_URL = "brandUrlAgg";
    private static final String ATTR_NAME_KEY = "nameList";
    private static final String ATTR_VALUE_KEY = "valueList";

    /** ES */
    @Autowired
    @Qualifier("elasticsearchRestTemplate")
    private ElasticsearchTemplate restTemplate;

    @Autowired
    private IEsGoodsIndexService esGoodsIndexService;
    /** 缓存 */
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public SearchPage<EsGoodsIndex> searchGoods(EsGoodsSearchQuery esGoodsSearchQuery) {
        boolean exists = restTemplate.indexOps(EsGoodsIndex.class).exists();
        if (!exists) {
            esGoodsIndexService.init();
        }

        if (CharSequenceUtil.isNotEmpty(esGoodsSearchQuery.getKeyword())) {
            redisRepository.hincr(CachePrefix.HOT_WORD.getPrefix(), esGoodsSearchQuery.getKeyword(), 1.0);
        }

        NativeSearchQueryBuilder searchQueryBuilder = createSearchQueryBuilder(esGoodsSearchQuery);
        NativeSearchQuery searchQuery = searchQueryBuilder.build();
        LogUtils.info("searchGoods DSL:{}", searchQuery.getQuery());
        SearchHits<EsGoodsIndex> search = restTemplate.search(searchQuery, EsGoodsIndex.class);
        return SearchHitSupport.searchPageFor(search, searchQuery.getPageable());
    }

    @Override
    public List<String> getHotWords(Integer count) {
        if (count == null) {
            count = 0;
        }
        List<String> hotWords = new ArrayList<>();
        // redis 排序中，下标从0开始，所以这里需要 -1 处理 reverseRangeWithScores
        count = count - 1;
        Set<TypedTuple<Object>> set =
                redisRepository.zReverseRangeByScoreWithScores(CachePrefix.HOT_WORD.getPrefix(), 0, count);
        if (set == null || set.isEmpty()) {
            return new ArrayList<>();
        }
        for (TypedTuple<Object> defaultTypedTuple : set) {
            hotWords.add(Objects.requireNonNull(defaultTypedTuple.getValue()).toString());
        }
        return hotWords;
    }

    @Override
    public boolean setHotWords(HotWordsDTO hotWords) {
        redisRepository.hincr(
                CachePrefix.HOT_WORD.getPrefix(), hotWords.getKeywords(), Double.valueOf(hotWords.getPoint()));
        return true;
    }

    /**
     * 删除热门关键词
     *
     * @param keywords 热词
     */
    @Override
    public boolean deleteHotWords(String keywords) {
        redisRepository.del(CachePrefix.HOT_WORD.getPrefix(), keywords);
        return true;
    }

    @Override
    public EsGoodsRelatedInfo getSelector(EsGoodsSearchQuery esGoodsSearchQuery) {
        NativeSearchQueryBuilder builder = createSearchQueryBuilder(esGoodsSearchQuery);
        // 分类
        AggregationBuilder categoryNameBuilder =
                AggregationBuilders.terms("categoryNameAgg").field("categoryNamePath.keyword");
        builder.withAggregations(
                AggregationBuilders.terms("categoryAgg").field("categoryPath").subAggregation(categoryNameBuilder));

        // 品牌
        AggregationBuilder brandNameBuilder =
                AggregationBuilders.terms(ATTR_BRAND_NAME).field("brandName.keyword");
        builder.withAggregations(AggregationBuilders.terms("brandIdNameAgg")
                .field(ATTR_BRAND_ID)
                .size(Integer.MAX_VALUE)
                .subAggregation(brandNameBuilder));
        AggregationBuilder brandUrlBuilder =
                AggregationBuilders.terms(ATTR_BRAND_URL).field("brandUrl.keyword");
        builder.withAggregations(AggregationBuilders.terms("brandIdUrlAgg")
                .field(ATTR_BRAND_ID)
                .size(Integer.MAX_VALUE)
                .subAggregation(brandUrlBuilder));

        // 参数
        AggregationBuilder valuesBuilder = AggregationBuilders.terms("valueAgg").field(ATTR_VALUE);
        AggregationBuilder sortBuilder = AggregationBuilders.sum("sortAgg").field(ATTR_SORT);
        AggregationBuilder paramsNameBuilder = AggregationBuilders.terms("nameAgg")
                .field(ATTR_NAME)
                .subAggregation(sortBuilder)
                .order(BucketOrder.aggregation("sortAgg", false))
                .subAggregation(valuesBuilder);
        builder.withAggregations(
                AggregationBuilders.nested("attrAgg", ATTR_PATH).subAggregation(paramsNameBuilder));
        NativeSearchQuery searchQuery = builder.build();
        SearchHits<EsGoodsIndex> search = restTemplate.search(searchQuery, EsGoodsIndex.class);

        LogUtils.info("getSelector DSL:{}", searchQuery.getQuery());
        // Map<String, Aggregation> aggregationMap =
        // Objects.requireNonNull(search.getAggregations())
        //	.getAsMap();
        // return convertToEsGoodsRelatedInfo(aggregationMap, goodsSearch);

        return null;
    }

    @Override
    public List<EsGoodsIndex> getEsGoodsBySkuIds(List<Long> skuIds) {
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery build = searchQueryBuilder.build();
        build.setIds(skuIds.stream().map(String::valueOf).toList());

        //		return restTemplate.multiGet(build, EsGoodsIndex.class,
        //			restTemplate.getIndexCoordinatesFor(EsGoodsIndex.class));
        return null;
    }

    @Override
    public EsGoodsIndex getEsGoodsById(Long id) {
        return this.restTemplate.get(String.valueOf(id), EsGoodsIndex.class);
    }

    /**
     * 转换搜索结果为聚合商品展示信息
     *
     * @param aggregationMap 搜索结果
     * @return 聚合商品展示信息
     */
    private EsGoodsRelatedInfo convertToEsGoodsRelatedInfo(
            Map<String, Aggregation> aggregationMap, EsGoodsSearchQuery goodsSearch) {
        EsGoodsRelatedInfo esGoodsRelatedInfo = new EsGoodsRelatedInfo();
        // 分类
        List<SelectorOptions> categoryOptions = new ArrayList<>();
        ParsedStringTerms categoryTerms = (ParsedStringTerms) aggregationMap.get("categoryAgg");
        List<? extends Terms.Bucket> categoryBuckets = categoryTerms.getBuckets();
        if (categoryBuckets != null && !categoryBuckets.isEmpty()) {
            categoryOptions = this.convertCategoryOptions(categoryBuckets);
        }
        esGoodsRelatedInfo.setCategories(categoryOptions);

        // 品牌
        ParsedStringTerms brandNameTerms = (ParsedStringTerms) aggregationMap.get("brandIdNameAgg");
        ParsedStringTerms brandUrlTerms = (ParsedStringTerms) aggregationMap.get("brandIdUrlAgg");
        List<? extends Terms.Bucket> brandBuckets = brandNameTerms.getBuckets();
        List<? extends Terms.Bucket> brandUrlBuckets = brandUrlTerms.getBuckets();
        List<SelectorOptions> brandOptions = new ArrayList<>();
        if (brandBuckets != null && !brandBuckets.isEmpty()) {
            brandOptions = this.convertBrandOptions(goodsSearch, brandBuckets, brandUrlBuckets);
        }
        esGoodsRelatedInfo.setBrands(brandOptions);

        // 参数
        ParsedNested attrTerms = (ParsedNested) aggregationMap.get("attrAgg");
        if (!goodsSearch.getNotShowCol().isEmpty()) {
            if (goodsSearch.getNotShowCol().containsKey(ATTR_NAME_KEY)
                    && goodsSearch.getNotShowCol().containsKey(ATTR_VALUE_KEY)) {
                esGoodsRelatedInfo.setParamOptions(
                        buildGoodsParam(attrTerms, goodsSearch.getNotShowCol().get(ATTR_NAME_KEY)));
            }
        } else {
            esGoodsRelatedInfo.setParamOptions(buildGoodsParam(attrTerms, null));
        }

        return esGoodsRelatedInfo;
    }

    /**
     * 将品牌聚合结果转换品牌选择项
     *
     * @param goodsSearch 查询参数
     * @param brandBuckets 品牌聚合结果桶
     * @param brandUrlBuckets 品牌地址聚合结果桶
     * @return 品牌选择项列表
     */
    private List<SelectorOptions> convertBrandOptions(
            EsGoodsSearchQuery goodsSearch,
            List<? extends Terms.Bucket> brandBuckets,
            List<? extends Terms.Bucket> brandUrlBuckets) {
        List<SelectorOptions> brandOptions = new ArrayList<>();
        for (int i = 0; i < brandBuckets.size(); i++) {
            String brandId = brandBuckets.get(i).getKey().toString();
            // 当商品品牌id为0时，代表商品没有选择品牌，所以过滤掉品牌选择器
            // 当品牌id为空并且
            if ("0".equals(brandId)
                    || (CharSequenceUtil.isNotEmpty(goodsSearch.getBrandId())
                            && Arrays.asList(goodsSearch.getBrandId().split("@"))
                                    .contains(brandId))) {
                continue;
            }

            String brandName = "";
            if (brandBuckets.get(i).getAggregations() != null
                    && brandBuckets.get(i).getAggregations().get(ATTR_BRAND_NAME) != null) {
                brandName = this.getAggregationsBrandOptions(
                        brandBuckets.get(i).getAggregations().get(ATTR_BRAND_NAME));
                if (StringUtils.isEmpty(brandName)) {
                    continue;
                }
            }

            String brandUrl = "";
            if (brandUrlBuckets != null
                    && !brandUrlBuckets.isEmpty()
                    && brandUrlBuckets.get(i).getAggregations() != null
                    && brandUrlBuckets.get(i).getAggregations().get(ATTR_BRAND_URL) != null) {
                brandUrl = this.getAggregationsBrandOptions(
                        brandUrlBuckets.get(i).getAggregations().get(ATTR_BRAND_URL));
                if (StringUtils.isEmpty(brandUrl)) {
                    continue;
                }
            }
            SelectorOptions so = new SelectorOptions();
            so.setName(brandName);
            so.setValue(brandId);
            so.setUrl(brandUrl);
            brandOptions.add(so);
        }
        return brandOptions;
    }

    /**
     * 获取品牌聚合结果内的参数
     *
     * @param brandAgg 品牌聚合结果
     * @return 品牌聚合结果内的参数
     */
    private String getAggregationsBrandOptions(ParsedStringTerms brandAgg) {
        List<? extends Terms.Bucket> brandAggBuckets = brandAgg.getBuckets();
        if (brandAggBuckets != null && !brandAggBuckets.isEmpty()) {
            return brandAggBuckets.get(0).getKey().toString();
        }
        return "";
    }

    /**
     * 将分类聚合结果转换分类选择项
     *
     * @param categoryBuckets 分类聚合结果
     * @return 分类选择项集合
     */
    private List<SelectorOptions> convertCategoryOptions(List<? extends Terms.Bucket> categoryBuckets) {
        List<SelectorOptions> categoryOptions = new ArrayList<>();
        for (Terms.Bucket categoryBucket : categoryBuckets) {
            String categoryPath = categoryBucket.getKey().toString();
            ParsedStringTerms categoryNameAgg = categoryBucket.getAggregations().get("categoryNameAgg");
            List<? extends Terms.Bucket> categoryNameBuckets = categoryNameAgg.getBuckets();

            String categoryNamePath = categoryPath;
            if (!categoryNameBuckets.isEmpty()) {
                categoryNamePath = categoryNameBuckets.get(0).getKey().toString();
            }
            String[] split = ArrayUtil.distinct(categoryPath.split(","));
            String[] nameSplit = categoryNamePath.split(",");
            if (split.length == nameSplit.length) {
                for (int i = 0; i < split.length; i++) {
                    SelectorOptions so = new SelectorOptions();
                    so.setName(nameSplit[i]);
                    so.setValue(split[i]);
                    if (!categoryOptions.contains(so)) {
                        categoryOptions.add(so);
                    }
                }
            }
        }
        return categoryOptions;
    }

    /**
     * 构建商品参数信息
     *
     * @param attrTerms 商品参数搜索结果
     * @param nameList 查询的规格名
     * @return 商品参数信息
     */
    private List<ParamOptions> buildGoodsParam(ParsedNested attrTerms, List<String> nameList) {
        if (attrTerms != null) {
            Aggregations attrAggregations = attrTerms.getAggregations();
            Map<String, Aggregation> attrMap = attrAggregations.getAsMap();
            ParsedStringTerms nameAgg = (ParsedStringTerms) attrMap.get("nameAgg");

            if (nameAgg != null) {
                return this.buildGoodsParamOptions(nameAgg, nameList);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 构造商品参数属性
     *
     * @param nameAgg 商品参数聚合内容
     * @param nameList 查询的规格名
     * @return 商品参数属性集合
     */
    private List<ParamOptions> buildGoodsParamOptions(ParsedStringTerms nameAgg, List<String> nameList) {
        List<ParamOptions> paramOptions = new ArrayList<>();
        List<? extends Terms.Bucket> nameBuckets = nameAgg.getBuckets();

        for (Terms.Bucket bucket : nameBuckets) {
            String name = bucket.getKey().toString();
            ParamOptions paramOptions1 = new ParamOptions();
            ParsedStringTerms valueAgg = bucket.getAggregations().get("valueAgg");
            List<? extends Terms.Bucket> valueBuckets = valueAgg.getBuckets();
            List<String> valueSelectorList = new ArrayList<>();

            for (Terms.Bucket valueBucket : valueBuckets) {
                String value = valueBucket.getKey().toString();

                if (CharSequenceUtil.isNotEmpty(value)) {
                    valueSelectorList.add(value);
                }
            }
            if (nameList == null || !nameList.contains(name)) {
                paramOptions1.setKey(name);
                paramOptions1.setValues(valueSelectorList);
                paramOptions.add(paramOptions1);
            }
        }
        return paramOptions;
    }

    /**
     * 创建es搜索builder
     *
     * @param esGoodsSearchQuery 搜索条件
     * @return es搜索builder
     */
    private NativeSearchQueryBuilder createSearchQueryBuilder(EsGoodsSearchQuery esGoodsSearchQuery) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (esGoodsSearchQuery != null) {
            int pageNumber = esGoodsSearchQuery.getCurrentPage() - 1;
            if (pageNumber < 0) {
                pageNumber = 0;
            }
            Pageable pageable = PageRequest.of(pageNumber, esGoodsSearchQuery.getPageSize());
            // 分页
            nativeSearchQueryBuilder.withPageable(pageable);
        }

        // 查询参数非空判定
        if (esGoodsSearchQuery != null) {
            // 过滤条件
            BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();

            // 对查询条件进行处理
            this.commonSearch(filterBuilder, esGoodsSearchQuery);

            // 未上架的商品不显示
            filterBuilder.must(QueryBuilders.matchQuery("marketEnable", GoodsStatusEnum.UPPER.name()));
            // 待审核和审核不通过的商品不显示
            filterBuilder.must(QueryBuilders.matchQuery("authFlag", GoodsAuthEnum.PASS.name()));

            // 关键字检索
            if (CharSequenceUtil.isEmpty(esGoodsSearchQuery.getKeyword())) {
                List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
                GaussDecayFunctionBuilder skuNoScore = ScoreFunctionBuilders.gaussDecayFunction("skuSource", 100, 10)
                        .setWeight(10);
                FunctionScoreQueryBuilder.FilterFunctionBuilder skuNoBuilder =
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchAllQuery(), skuNoScore);
                filterFunctionBuilders.add(skuNoBuilder);
                FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
                filterFunctionBuilders.toArray(builders);
                FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                        .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                        .setMinScore(2);
                // 聚合搜索则将结果放入过滤条件
                filterBuilder.must(functionScoreQueryBuilder);
            } else {
                this.keywordSearch(filterBuilder, esGoodsSearchQuery.getKeyword());
            }

            // 如果是聚合查询
            nativeSearchQueryBuilder.withQuery(filterBuilder);

            if (esGoodsSearchQuery != null
                    && CharSequenceUtil.isNotEmpty(esGoodsSearchQuery.getOrder())
                    && CharSequenceUtil.isNotEmpty(esGoodsSearchQuery.getSort())) {
                nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort(esGoodsSearchQuery.getSort())
                        .order(SortOrder.valueOf(esGoodsSearchQuery.getOrder().toUpperCase())));
            } else {
                nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
            }
        }
        return nativeSearchQueryBuilder;
    }

    /**
     * 查询属性处理
     *
     * @param filterBuilder 过滤构造器
     * @param searchDTO 查询参数
     */
    /**
     * 查询属性处理
     *
     * @param filterBuilder 过滤构造器
     * @param searchDTO 查询参数
     */
    private void commonSearch(BoolQueryBuilder filterBuilder, EsGoodsSearchDTO searchDTO) {
        // 品牌判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getBrandId())) {
            String[] brands = searchDTO.getBrandId().split("@");
            filterBuilder.must(QueryBuilders.termsQuery(ATTR_BRAND_ID, brands));
        }
        if (searchDTO.getRecommend() != null) {
            filterBuilder.filter(QueryBuilders.termQuery("recommend", searchDTO.getRecommend()));
        }
        // 规格项判定
        if (searchDTO.getNameIds() != null && !searchDTO.getNameIds().isEmpty()) {
            filterBuilder.must(QueryBuilders.nestedQuery(
                    ATTR_PATH, QueryBuilders.termsQuery("attrList.nameId", searchDTO.getNameIds()), ScoreMode.None));
        }
        // 分类判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getCategoryId())) {
            filterBuilder.must(QueryBuilders.wildcardQuery("categoryPath", "*" + searchDTO.getCategoryId() + "*"));
        }
        // 店铺分类判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getStoreCatId())) {
            filterBuilder.must(QueryBuilders.wildcardQuery("storeCategoryPath", "*" + searchDTO.getStoreCatId() + "*"));
        }
        // 店铺判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getStoreId())) {
            filterBuilder.filter(QueryBuilders.termQuery("storeId", searchDTO.getStoreId()));
        }
        // 属性判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getProp())) {
            this.propSearch(filterBuilder, searchDTO);
        }
        // 价格区间判定
        if (CharSequenceUtil.isNotEmpty(searchDTO.getPrice())) {
            String[] prices = searchDTO.getPrice().split("_");
            if (prices.length == 0) {
                return;
            }
            double min = Convert.toDouble(prices[0], 0.0);
            double max = Integer.MAX_VALUE;

            if (prices.length == 2) {
                max = Convert.toDouble(prices[1], Double.MAX_VALUE);
            }
            if (min > max) {
                throw new ServiceException("价格区间错误");
            }
            if (min > Double.MAX_VALUE) {
                min = Double.MAX_VALUE;
            }
            if (max > Double.MAX_VALUE) {
                max = Double.MAX_VALUE;
            }
            filterBuilder.must(QueryBuilders.rangeQuery("price")
                    .from(min)
                    .to(max)
                    .includeLower(true)
                    .includeUpper(true));
        }
    }

    /**
     * 商品参数查询处理
     *
     * @param filterBuilder 过滤构造器
     * @param searchDTO 查询参数
     */
    private void propSearch(BoolQueryBuilder filterBuilder, EsGoodsSearchQuery searchDTO) {
        String[] props = searchDTO.getProp().split("@");
        List<String> nameList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        Map<String, List<String>> valueMap = new HashMap<>(16);
        for (String prop : props) {
            String[] propValues = prop.split("_");
            String name = propValues[0];
            String value = propValues[1];
            if (!nameList.contains(name)) {
                nameList.add(name);
            }
            if (!valueList.contains(value)) {
                valueList.add(value);
            }
            // 将同一规格名下的规格值分组
            if (!valueMap.containsKey(name)) {
                List<String> values = new ArrayList<>();
                values.add(value);
                valueMap.put(name, values);
            } else {
                valueMap.get(name).add(value);
            }
        }
        // 遍历所有的规格
        for (Map.Entry<String, List<String>> entry : valueMap.entrySet()) {
            filterBuilder.must(QueryBuilders.nestedQuery(
                    ATTR_PATH, QueryBuilders.matchQuery(ATTR_NAME, entry.getKey()), ScoreMode.None));
            BoolQueryBuilder shouldBuilder = QueryBuilders.boolQuery();
            for (String s : entry.getValue()) {
                shouldBuilder.should(
                        QueryBuilders.nestedQuery(ATTR_PATH, QueryBuilders.matchQuery(ATTR_VALUE, s), ScoreMode.None));
            }
            filterBuilder.must(shouldBuilder);
        }
        searchDTO.getNotShowCol().put(ATTR_NAME_KEY, nameList);
        searchDTO.getNotShowCol().put(ATTR_VALUE_KEY, valueList);
    }

    /**
     * 关键字查询处理
     *
     * @param filterBuilder 过滤构造器
     * @param keyword 关键字
     */
    private void keywordSearch(BoolQueryBuilder filterBuilder, String keyword) {
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
        if (keyword.contains(" ")) {
            for (String s : keyword.split(" ")) {
                filterFunctionBuilders.addAll(this.buildKeywordSearch(s));
            }
        } else {
            filterFunctionBuilders = this.buildKeywordSearch(keyword);
        }

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
        filterFunctionBuilders.toArray(builders);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .setMinScore(2);
        // 聚合搜索则将结果放入过滤条件
        filterBuilder.must(functionScoreQueryBuilder);
    }

    /**
     * 构造关键字查询
     *
     * @param keyword 关键字
     * @return 构造查询的集合
     */
    private List<FunctionScoreQueryBuilder.FilterFunctionBuilder> buildKeywordSearch(String keyword) {
        List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
        MatchQueryBuilder goodsNameQuery =
                QueryBuilders.matchQuery("goodsName", keyword).operator(Operator.AND);
        // 分词匹配
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                goodsNameQuery, ScoreFunctionBuilders.weightFactorFunction(10)));
        // 属性匹配
        filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                QueryBuilders.nestedQuery(
                        ATTR_PATH, QueryBuilders.wildcardQuery(ATTR_VALUE, "*" + keyword + "*"), ScoreMode.None),
                ScoreFunctionBuilders.weightFactorFunction(8)));

        GaussDecayFunctionBuilder skuNoScore =
                ScoreFunctionBuilders.gaussDecayFunction("skuSource", 100, 10).setWeight(7);
        FunctionScoreQueryBuilder.FilterFunctionBuilder skuNoBuilder =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(goodsNameQuery, skuNoScore);
        filterFunctionBuilders.add(skuNoBuilder);

        FieldValueFactorFunctionBuilder buyCountScore = ScoreFunctionBuilders.fieldValueFactorFunction("buyCount")
                .modifier(FieldValueFactorFunction.Modifier.LOG1P)
                .setWeight(6);
        FunctionScoreQueryBuilder.FilterFunctionBuilder buyCountBuilder =
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(goodsNameQuery, buyCountScore);
        filterFunctionBuilders.add(buyCountBuilder);
        return filterFunctionBuilders;
    }
}
