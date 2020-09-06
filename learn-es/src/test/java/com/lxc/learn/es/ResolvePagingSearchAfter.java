package com.lxc.learn.es;

import com.lxc.learn.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.searchafter.SearchAfterBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/7 19:43
 */
@Slf4j
public class ResolvePagingSearchAfter extends BaseTest {

    private static Integer size = 10;

    /**
     * 可以支持多用户在线并发操作
     * 说白了 search_after 并没有解决随机跳页查询的场景，但是可以支撑多query并发请求；
     * search_after 操作需要指定一个支持排序且值唯一的字段用来做下一页拉取的指针，
     * 这种翻页方式也可以通过bool查询的range filter实现。
     */

    @Test
    public void searchAfter() {
        Object[] objects = new Object[]{"1"};
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        boolean type = true;
        while (type) {
            SearchHits searchHits = searchAfter(objects);
            SearchHit[] hits = searchHits.getHits();
            if (hits != null && hits.length > 0){
                objects = hits[hits.length-1].getSortValues();
                if (hits.length < size) type = false;
                for (SearchHit hit : hits) {
                    data.add(hit.getSourceAsMap());
                    System.out.println(JsonUtil.objectToJson(hit.getSourceAsMap()));
                }
            }

        }
        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }
        System.out.println(data.size() + "-----------------");

    }

    public SearchHits searchAfter(Object[] objects) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("age", "33"));
        sourceBuilder.size(size);
        sourceBuilder.sort("account_number", SortOrder.ASC);
        sourceBuilder.searchAfter(objects);


        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("bank");
        searchRequest.source(sourceBuilder);
        ActionFuture<SearchResponse> response = elasticsearchTemplate.getClient().search(searchRequest);

        SearchHits searchHits = response.actionGet().getHits();
        return searchHits;
    }





   public void test(){
       RestClient restClient = RestClient.builder(
               new HttpHost("localhost", 9200, "http")).build();
   }

    private static IndexSearcher indexSearcher;

    //文件索引对象

    static {
        try {
            Directory directory = FSDirectory.open(Paths.get("bank"));
            IndexReader reader = DirectoryReader.open(directory);
            indexSearcher = new IndexSearcher(reader);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }

    }

    public void scrollAfter() throws Exception{

        SearchAfterBuilder searchAfterBuilder = new SearchAfterBuilder();
        searchAfterBuilder.setSortValues(new Object[]{"0"});
        FieldDoc fieldDoc = null;

    }

}
