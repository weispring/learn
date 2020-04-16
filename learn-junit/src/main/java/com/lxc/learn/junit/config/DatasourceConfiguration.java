//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.lxc.learn.junit.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@ConditionalOnProperty(
        prefix = "mybatisplus.generic",
        name = {"enabled"},
        havingValue = "true"
)
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
public class DatasourceConfiguration {
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired
    WallFilter wallFilter;

    public DatasourceConfiguration() {
    }

    @Bean(
            name = {"customDataSource"}
    )
    @ConfigurationProperties("spring.datasource.druid")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(this.dataSourceProperties.determineDriverClassName());
        dataSource.setUrl(this.dataSourceProperties.determineUrl());
        dataSource.setUsername(this.dataSourceProperties.determineUsername());
        dataSource.setPassword(this.dataSourceProperties.determinePassword());
        DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(this.dataSourceProperties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }

        List<Filter> filters = new ArrayList();
        filters.add(this.wallFilter);
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    @Bean(
            name = {"customInterceptors"}
    )
    public Interceptor[] customInterceptors() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return new Interceptor[]{paginationInterceptor};
    }

    @Bean(
            name = {"wallFilter"}
    )
    @DependsOn({"wallConfig"})
    public WallFilter wallFilter(WallConfig wallConfig) {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig);
        return wallFilter;
    }

    @Bean(
            name = {"wallConfig"}
    )
    public WallConfig wallConfig() {
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);
        wallConfig.setNoneBaseStatementAllow(true);
        return wallConfig;
    }
}

