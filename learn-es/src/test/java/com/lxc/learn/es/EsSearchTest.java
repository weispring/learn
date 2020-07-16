package com.lxc.learn.es;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.es.document.db.AccountIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/6 11:51
 */
@Slf4j
public class EsSearchTest extends BaseTest{

    public void template(){
        NativeSearchQueryBuilder nativeSearch = new NativeSearchQueryBuilder();

        //query
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        nativeSearch.withQuery(boolQuery);

        //聚合
        AbstractAggregationBuilder keywordAgg = AggregationBuilders.avg("name").field("field");
        nativeSearch.addAggregation(keywordAgg);

        //排序
        nativeSearch.withSort(SortBuilders.fieldSort("field").order(SortOrder.DESC));

    }

    /**
     {
     "size": 0,
     "aggs": {
     "all_tags": {
     "terms": {
     "field": "tags.keyword",
     "order": { "avg_price": "desc" } // 根据下述统计的结果排序
     },
     "aggs": {
     "avg_price": {
     "avg": { "field": "price" }
     }
     }
     }
     }
     }
     */

    @Test
    public void testAggregations(){
        NativeSearchQueryBuilder nativeSearch = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        nativeSearch.withQuery(boolQuery);
        boolQuery.must(QueryBuilders.termQuery("age", 33));
        //聚合
        //AbstractAggregationBuilder keywordAgg = AggregationBuilders.avg("all_state").field("state");
        TermsAggregationBuilder termsAggBuilder = AggregationBuilders.terms("all_state").field("state.keyword");
        termsAggBuilder.order(Terms.Order.aggregation("balanceTag",false)).size(5);

        AbstractAggregationBuilder avg = AggregationBuilders.avg("balanceTag").field("balance");
        termsAggBuilder.subAggregation(avg);
        nativeSearch.addAggregation(termsAggBuilder);
        //排序
        //nativeSearch.withSort(SortBuilders.fieldSort("state").order(SortOrder.DESC));

        AggregatedPage<AccountIndex> pageInfo = testSearch(nativeSearch);

        List<String> keywordList = new ArrayList<>();
        Aggregations aggs = pageInfo.getAggregations();
        Terms termsBrandId = aggs.get("all_state");
        for (Terms.Bucket bucket : termsBrandId.getBuckets()) {
            long docCount = bucket.getDocCount(); // Doc count
            log.info("brand-key " + bucket.getKey() + " doc_count " + docCount + "," +  JSON.toJSONString(bucket.getAggregations()));
            keywordList.add(String.valueOf(bucket.getKey()) + ":" + docCount);
        }
        Resp response = RespUtil.convertResult(true);
        response.setBody(keywordList);
    }


    @Test
    public void testFilter(){
        NativeSearchQueryBuilder nativeSearch = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        nativeSearch.withQuery(boolQuery);
        boolQuery.must(QueryBuilders.termQuery("age", 33));

        // filter
        BoolQueryBuilder filterBuilder = QueryBuilders.boolQuery();
        filterBuilder.must(QueryBuilders.termsQuery("age", Arrays.asList("33")));
        filterBuilder.must(QueryBuilders.rangeQuery("balance").gte(2000).lte(9000));
        nativeSearch.withFilter(filterBuilder);
        //分页
        //nativeSearch.withPageable();
        //排序
        nativeSearch.withSort(SortBuilders.fieldSort("state").order(SortOrder.DESC));

        AggregatedPage<AccountIndex> pageInfo = testSearch(nativeSearch);

        List<AccountIndex> keywordList = new ArrayList<>();
        for (AccountIndex accountIndex : pageInfo.getContent()) {
            log.info("{}", JsonUtil.objectToJson(accountIndex));
            keywordList.add(accountIndex);
        }
        Resp response = RespUtil.convertResult(true);
        response.setBody(keywordList);
    }


}
