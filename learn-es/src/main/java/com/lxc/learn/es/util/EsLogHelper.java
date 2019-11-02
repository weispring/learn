package com.lxc.learn.es.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/6 10:06
 */
@Slf4j
@Component
public class EsLogHelper {

    @Autowired
    private ElasticsearchConverter elasticsearchConverter;

    @Autowired
    private Client client;


    public void log(SearchQuery query, Class clazz){
        this.doSearch(this.prepareSearch(query, clazz), query);
    }

    private void doSearch(SearchRequestBuilder searchRequest, SearchQuery searchQuery) {
        if (searchQuery.getFilter() != null) {
            searchRequest.setPostFilter(searchQuery.getFilter());
        }

        Iterator var3;
        if (!CollectionUtils.isEmpty(searchQuery.getElasticsearchSorts())) {
            var3 = searchQuery.getElasticsearchSorts().iterator();

            while(var3.hasNext()) {
                SortBuilder sort = (SortBuilder)var3.next();
                searchRequest.addSort(sort);
            }
        }

        if (!searchQuery.getScriptFields().isEmpty()) {
            var3 = searchQuery.getScriptFields().iterator();

            while(var3.hasNext()) {
                ScriptField scriptedField = (ScriptField)var3.next();
                searchRequest.addScriptField(scriptedField.fieldName(), scriptedField.script());
            }
        }

        if (searchQuery.getHighlightFields() != null) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            HighlightBuilder.Field[] var10 = searchQuery.getHighlightFields();
            int var5 = var10.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                HighlightBuilder.Field highlightField = var10[var6];
                highlightBuilder.field(highlightField);
            }

            searchRequest.highlighter(highlightBuilder);
        }

        if (!CollectionUtils.isEmpty(searchQuery.getIndicesBoost())) {
            var3 = searchQuery.getIndicesBoost().iterator();

            while(var3.hasNext()) {
                IndexBoost indexBoost = (IndexBoost)var3.next();
                searchRequest.addIndexBoost(indexBoost.getIndexName(), indexBoost.getBoost());
            }
        }

        if (!CollectionUtils.isEmpty(searchQuery.getAggregations())) {
            var3 = searchQuery.getAggregations().iterator();

            while(var3.hasNext()) {
                AbstractAggregationBuilder aggregationBuilder = (AbstractAggregationBuilder)var3.next();
                searchRequest.addAggregation(aggregationBuilder);
            }
        }

        if (!CollectionUtils.isEmpty(searchQuery.getFacets())) {
            var3 = searchQuery.getFacets().iterator();

            while(var3.hasNext()) {
                FacetRequest aggregatedFacet = (FacetRequest)var3.next();
                searchRequest.addAggregation(aggregatedFacet.getFacet());
            }
        }
        searchRequest.setQuery(searchQuery.getQuery());
        //return this.getSearchResponse(searchRequest.setQuery(searchQuery.getQuery()).execute());
        log.info("完整dsl:{}",searchRequest.toString());
    }


    private <T> SearchRequestBuilder prepareSearch(Query query, Class<T> clazz) {
        this.setPersistentEntityIndexAndType(query, clazz);
        return this.prepareSearch(query);
    }


    private void setPersistentEntityIndexAndType(Query query, Class clazz) {
        if (query.getIndices().isEmpty()) {
            query.addIndices(this.retrieveIndexNameFromPersistentEntity(clazz));
        }

        if (query.getTypes().isEmpty()) {
            query.addTypes(this.retrieveTypeFromPersistentEntity(clazz));
        }

    }



    private SearchRequestBuilder prepareSearch(Query query) {
        Assert.notNull(query.getIndices(), "No index defined for Query");
        Assert.notNull(query.getTypes(), "No type defined for Query");
        int startRecord = 0;
        SearchRequestBuilder searchRequestBuilder = this.client.prepareSearch(toArray(query.getIndices())).setSearchType(query.getSearchType()).setTypes(toArray(query.getTypes())).setVersion(true);
        if (query.getSourceFilter() != null) {
            SourceFilter sourceFilter = query.getSourceFilter();
            searchRequestBuilder.setFetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
        }

        if (query.getPageable().isPaged()) {
            startRecord = query.getPageable().getPageNumber() * query.getPageable().getPageSize();
            searchRequestBuilder.setSize(query.getPageable().getPageSize());
        }

        searchRequestBuilder.setFrom(startRecord);
        if (!query.getFields().isEmpty()) {
            searchRequestBuilder.setFetchSource(toArray(query.getFields()), (String[])null);
        }

        FieldSortBuilder sort;
        if (query.getSort() != null) {
            for(Iterator var7 = query.getSort().iterator(); var7.hasNext(); searchRequestBuilder.addSort(sort)) {
                Sort.Order order = (Sort.Order)var7.next();
                sort = (FieldSortBuilder) SortBuilders.fieldSort(order.getProperty()).order(order.getDirection().isDescending() ? SortOrder.DESC : SortOrder.ASC);
                if (order.getNullHandling() == Sort.NullHandling.NULLS_FIRST) {
                    sort.missing("_first");
                } else if (order.getNullHandling() == Sort.NullHandling.NULLS_LAST) {
                    sort.missing("_last");
                }
            }
        }

        if (query.getMinScore() > 0.0F) {
            searchRequestBuilder.setMinScore(query.getMinScore());
        }

        return searchRequestBuilder;
    }


    private String[] retrieveIndexNameFromPersistentEntity(Class clazz) {
        return clazz != null ? new String[]{this.getPersistentEntityFor(clazz).getIndexName()} : null;
    }

    public ElasticsearchPersistentEntity getPersistentEntityFor(Class clazz) {
        Assert.isTrue(clazz.isAnnotationPresent(Document.class), "Unable to identify index name. " + clazz.getSimpleName() + " is not a Document. Make sure the document class is annotated with @Document(indexName=\"foo\")");
        return (ElasticsearchPersistentEntity)this.elasticsearchConverter.getMappingContext().getRequiredPersistentEntity(clazz);
    }



    private String[] retrieveTypeFromPersistentEntity(Class clazz) {
        return clazz != null ? new String[]{this.getPersistentEntityFor(clazz).getIndexType()} : null;
    }

    private static String[] toArray(List<String> values) {
        String[] valuesAsArray = new String[values.size()];
        return (String[])values.toArray(valuesAsArray);
    }
}
