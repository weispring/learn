package com.lxc.learn.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.common.util.web.StringUtil;
import com.lxc.learn.es.document.db.AccountIndex;
import com.lxc.learn.es.model.request.AccountReqBody;
import com.lxc.learn.es.repository.AccountDbIndexRepository;
import com.lxc.learn.es.util.EsLogHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品搜索
 * Created by yangxinbo on 2017/7/25.
 */
@Slf4j
@Service
@AllArgsConstructor
public class AccountServiceImpl {

    private ElasticsearchTemplate elasticsearchTemplate;

    private AccountDbIndexRepository productDbIndexRepository;

    private EsLogHelper esLogHelper;

    /**
     *
     * @param param
     * @return
     */
    public Resp<List<String>> keyWord(AccountReqBody param) {
        // query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (StringUtil.isNotEmpty(param.getAccountNumber())) {
            boolQuery.must(QueryBuilders.termQuery("account_number", param.getAccountNumber()));
        }
        if (StringUtil.isNotEmpty(param.getAddress())) {
            boolQuery.must(QueryBuilders.termQuery("address", param.getAddress()));
        }
        if (StringUtil.isNotEmpty(param.getAge())) {
            boolQuery.must(QueryBuilders.termQuery("age", param.getAge()));
        }

        // 聚合 关键字
        NativeSearchQueryBuilder nativeSearch = new NativeSearchQueryBuilder();
        nativeSearch.withQuery(boolQuery);

        if (StringUtil.isNotEmpty(param.getGroupBy())){
            AbstractAggregationBuilder keywordAgg = AggregationBuilders.terms("group_by"+param.getGroupBy()).field(param.getGroupBy());
            nativeSearch.addAggregation(keywordAgg);
            if (StringUtil.isNotEmpty(param.getAvgField())){
                AbstractAggregationBuilder avgAff = AggregationBuilders.avg(param.getAvgField()).field(param.getAvgField());
                keywordAgg.subAggregation(avgAff);
            }
        }
        if (StringUtil.isNotEmpty(param.getAvgField())){
            AbstractAggregationBuilder keywordAgg = AggregationBuilders.avg(param.getAvgField()).field(param.getAvgField());
            nativeSearch.addAggregation(keywordAgg);
        }

        //排序
        if (StringUtil.isNotEmpty(param.getSortBy())){
            nativeSearch.withSort(SortBuilders.fieldSort(param.getSortBy()).order(SortOrder.DESC));
        }

        SearchQuery searchQuery = nativeSearch.build();
        //log.info("Filter DSL:\n{} \nQuery DSL:\n{}", searchQuery.getFilter(), searchQuery.getQuery());
        esLogHelper.log(searchQuery, AccountIndex.class);
        AggregatedPage<AccountIndex> pageInfo = elasticsearchTemplate.queryForPage(searchQuery, AccountIndex.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                return new DefaultResultMapper().mapResults(response, clazz, pageable);
            }
        });
        log.info("result:{}", JSON.toJSON(pageInfo).toString());
        List<String> keywordList = new ArrayList<>();
        Aggregations aggs = pageInfo.getAggregations();
        Terms termsBrandId = aggs.get("group_by"+param.getGroupBy());
        for (Terms.Bucket bucket : termsBrandId.getBuckets()) {
            long docCount = bucket.getDocCount(); // Doc count
            log.info("brand-key " + bucket.getKey() + " doc_count " + docCount);
            keywordList.add(String.valueOf(bucket.getKey()) + ":" + docCount);
        }
        Resp response = RespUtil.convertResult(true);
        response.setBody(keywordList);
        return response;
    }

    /**
     *
     * @param param
     * @return
     */
    public Resp<List<String>> search(AccountReqBody param) {
        // query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (StringUtil.isNotEmpty(param.getAccountNumber())) {
            boolQuery.must(QueryBuilders.termQuery("account_number", param.getAccountNumber()));
        }
        if (StringUtil.isNotEmpty(param.getAddress())) {
            boolQuery.must(QueryBuilders.termQuery("address", param.getAddress()));
        }

        // 聚合 关键字
        NativeSearchQueryBuilder nativeSearch = new NativeSearchQueryBuilder();
        nativeSearch.withQuery(boolQuery);
        SearchQuery searchQuery = nativeSearch.build();
        log.info("searchQuery:{} \n Query DSL:\n{}", JSON.toJSONString(searchQuery), searchQuery.getQuery());
        AggregatedPage<AccountIndex> pageInfo = elasticsearchTemplate.queryForPage(searchQuery, AccountIndex.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                return new DefaultResultMapper().mapResults(response, clazz, pageable);
            }
        });
        Resp response = RespUtil.convertResult(true);
        response.setBody(pageInfo.getContent());
        return response;
    }

}

