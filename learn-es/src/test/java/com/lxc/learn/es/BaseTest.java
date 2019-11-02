package com.lxc.learn.es;

import com.lxc.learn.es.document.db.AccountIndex;
import com.lxc.learn.es.util.EsLogHelper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/6 14:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class BaseTest {

    @Autowired
    protected EsLogHelper esLogHelper;
    @Autowired
    protected ElasticsearchTemplate elasticsearchTemplate;

    public AggregatedPage<AccountIndex> testSearch(NativeSearchQueryBuilder nativeSearch){
        SearchQuery searchQuery = nativeSearch.build();
        //log.info("Filter DSL:\n{} \nQuery DSL:\n{}", searchQuery.getFilter(), searchQuery.getQuery());
        esLogHelper.log(searchQuery, AccountIndex.class);
        AggregatedPage<AccountIndex> pageInfo = elasticsearchTemplate.queryForPage(searchQuery, AccountIndex.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                return new DefaultResultMapper().mapResults(response, clazz, pageable);
            }
        });
        log.info("result:{}", pageInfo.toString());
        return pageInfo;
    }


}
