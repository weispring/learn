package com.lxc.learn.junit.aop;

import com.lxc.learn.junit.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/7 18:02
 */
@Slf4j
abstract class TestAbstract {

    TestAbstract() {
    }


    @ConditionalOnClass({Application.class})
    @ConditionalOnProperty(
            name = {"spring.datasource.type"}
    )
    static class Generic {
        Generic() {
            System.out.println("GenericGenericGenericGeneric");
        }

        @Bean
        public String dataSource() {
            System.out.println("kkkkkkkkkkkkk");
            return new String("");
        }
    }

}
