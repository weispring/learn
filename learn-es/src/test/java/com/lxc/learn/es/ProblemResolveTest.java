package com.lxc.learn.es;

import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.es.document.db.AccountIndex;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

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
}
