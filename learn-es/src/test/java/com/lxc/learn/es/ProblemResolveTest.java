package com.lxc.learn.es;

import com.lxc.learn.es.document.db.AccountIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.slice.SliceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/6 14:43
 */
@Slf4j
public class ProblemResolveTest extends BaseTest{


    @Test
    public void scrollApi(){
        Pageable pageable = PageRequest.of(1, 10);
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("age","33");
        SearchQuery query = new NativeSearchQueryBuilder()
                .withIndices("bank")
                .withTypes("account")
                .withQuery(matchQueryBuilder)
                .withPageable(pageable)
                .build();
        long scollTimeInMillis = 10000;
        esLogHelper.log(query, AccountIndex.class);

        AggregatedPageImpl startPage = (AggregatedPageImpl)elasticsearchTemplate.startScroll(scollTimeInMillis, query, AccountIndex.class);
        String scrollId = startPage.getScrollId();

        if (startPage.hasContent()){
            for (Object s : startPage.getContent()){
                log.info("{}", JsonUtil.objectToJson(s));
            }
        }

        boolean hasRecords = true;
        long totalCount = 0;
        while (hasRecords) {
            Page<AccountIndex> page = elasticsearchTemplate.continueScroll(scrollId, scollTimeInMillis, AccountIndex.class);
            AggregatedPageImpl aggregatedPage = (AggregatedPageImpl) page;
            if (aggregatedPage.hasContent()) {
                log.info("page number:{}",aggregatedPage.getNumberOfElements());
                totalCount += aggregatedPage.getNumberOfElements();
                scrollId = aggregatedPage.getScrollId();
                for (Object s : aggregatedPage.getContent()){
                    log.info("{}", JsonUtil.objectToJson(s));
                }
            } else {
                hasRecords = false;
            }
        }
        //clear scroll
        elasticsearchTemplate.clearScroll(scrollId);
    }


    /**
     * 支持并行处理，执行时间要比scroll快很多。
     */
    @Test
    public void testSliceScroll() {
        //scroll slice分页检索
        Long starttime = System.currentTimeMillis();

        List<String> scrollIds = new ArrayList<>();
        int max = 5;
        long realTotalSize = 0;
        for (int i = 0; i < max; i++) {
            SliceBuilder sliceBuilder = new SliceBuilder("account_number",i,5);
            SearchResponse scrollResp = elasticsearchTemplate.getClient().prepareSearch("bank")
                    .setScroll(new TimeValue(60000))
                    .setQuery( QueryBuilders.boolQuery().must(QueryBuilders.termQuery("age", 33)))
                    .setSize(8)
                    .addSort("balance", SortOrder.ASC)
                    .slice(sliceBuilder)
                    .get();
            while (scrollResp.getHits().getHits().length != 0){
                for (SearchHit hit : scrollResp.getHits().getHits()) {
                    log.info("{}", JsonUtil.objectToJson(hit.getSourceAsMap()));
                }
                realTotalSize += scrollResp.getHits().getHits().length;
                scrollIds.add(scrollResp.getScrollId());
                scrollResp = elasticsearchTemplate.getClient().prepareSearchScroll(scrollResp.getScrollId())
                        .setScroll(new TimeValue(60000))
                        .execute()
                        .actionGet();
            }
        }
        //打印处理耗时和实际检索到的数据
        long endtime = System.currentTimeMillis();
        System.out.println("耗时："+(endtime - starttime)+",realTotalSize："+realTotalSize);


        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.setScrollIds(scrollIds);
        elasticsearchTemplate.getClient().clearScroll(clearScrollRequest);
    }


    @Test
    public void testParralSliceScroll() {
        //scroll slice分页检索
        Long starttime = System.currentTimeMillis();

        List<String> scrollIds = new ArrayList<>();
        int max = 5;
        final long[] realTotalSize = {0};
        final CountDownLatch countDownLatch = new CountDownLatch(max);
        for (int i = 0; i < max; i++) {
            int finalI = i;
            new Thread(new Runnable() {
              @Override
              public void run() {
                  SliceBuilder sliceBuilder = new SliceBuilder("account_number", finalI,5);
                  SearchResponse scrollResp = elasticsearchTemplate.getClient().prepareSearch("bank")
                          .setScroll(new TimeValue(60000))
                          .setQuery( QueryBuilders.boolQuery().must(QueryBuilders.termQuery("age", 33)))
                          .setSize(8)
                          .addSort("balance", SortOrder.ASC)
                          .slice(sliceBuilder)
                          .get();
                  while (scrollResp.getHits().getHits().length != 0){
                      for (SearchHit hit : scrollResp.getHits().getHits()) {
                          log.info("{}", JsonUtil.objectToJson(hit.getSourceAsMap()));
                      }
                      realTotalSize[0] += scrollResp.getHits().getHits().length;
                      scrollIds.add(scrollResp.getScrollId());
                      scrollResp = elasticsearchTemplate.getClient().prepareSearchScroll(scrollResp.getScrollId())
                              .setScroll(new TimeValue(60000))
                              .execute()
                              .actionGet();
                  }
                  countDownLatch.countDown();
              }
          }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印处理耗时和实际检索到的数据
        long endtime = System.currentTimeMillis();
        System.out.println("耗时："+(endtime - starttime)+",realTotalSize："+ realTotalSize[0]);


        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.setScrollIds(scrollIds);
        elasticsearchTemplate.getClient().clearScroll(clearScrollRequest);
    }



}
