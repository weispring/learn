package com.lxc.learn.es.document.db;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


/**
 * 商品
 */
@Getter
@Setter
@Document(indexName = AccountIndex.INDEX, type = AccountIndex.TYPE, refreshInterval = "-1", shards = 5, createIndex = false)
public class AccountIndex {

    public static final String INDEX = "bank";
    public static final String TYPE = "account";

    private Integer age;

    private String gender;

    private String address;

    private String employer;

    @Id
    @Field(type = FieldType.Text)
    private String email;

    private String city;

    private String state;

}
