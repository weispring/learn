package com.lxc.learn.es.repository;

import com.lxc.learn.es.document.db.AccountIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * 商品
 */
public interface AccountDbIndexRepository extends ElasticsearchRepository<AccountIndex, String> {
}
