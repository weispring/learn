package com.lxc.learn.junit.config;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
/**
 * @author lixianchun
 * @description
 * @date 2020/4/16
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


@Configuration
@EnableConfigurationProperties({MybatisProperties.class, MybatisplusCustomProperties.class})
@ConditionalOnProperty(
        prefix = "mybatisplus",
        name = {"enabled"},
        havingValue = "true"
)
public class MybatisConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MybatisConfiguration.class);
    @Autowired
    @Qualifier("customDataSource")
    private DataSource dataSource;
    @Autowired
    private MybatisProperties properties;
    @Autowired
    private MybatisplusCustomProperties mybatisplusCustomProperties;
    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();
    @Autowired
    @Qualifier("customInterceptors")
    private Interceptor[] customInterceptors;
    @Autowired(
            required = false
    )
    private DatabaseIdProvider databaseIdProvider;
    @Autowired
    private DataSourceProperties dataSourceProperties;

    public MybatisConfiguration() {
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(this.mybatisplusCustomProperties.getDialectType());
        return page;
    }

    @Bean(
            name = {"sqlSessionFactory"}
    )
    public SqlSessionFactory mybatisSqlSessionFactory() {
        try {
            MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
            sessionFactoryBean.setDataSource(this.dataSource);
            sessionFactoryBean.setVfs(SpringBootVFS.class);
            if (StringUtils.hasText(this.properties.getConfigLocation())) {
                sessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
            } else {
                com.baomidou.mybatisplus.MybatisConfiguration mc = new com.baomidou.mybatisplus.MybatisConfiguration();
                mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
                if (this.mybatisplusCustomProperties.isJdbcTypeForNull()) {
                    mc.setJdbcTypeForNull(JdbcType.NULL);
                }

                sessionFactoryBean.setConfiguration(mc);
            }

            if (!ObjectUtils.isEmpty(this.customInterceptors)) {
                sessionFactoryBean.setPlugins(this.customInterceptors);
            }

            GlobalConfiguration globalConfig = new GlobalConfiguration();
            globalConfig.setDbType(this.mybatisplusCustomProperties.getDialectType());
            globalConfig.setIdType(2);
            sessionFactoryBean.setGlobalConfig(globalConfig);
            if (this.databaseIdProvider != null) {
                sessionFactoryBean.setDatabaseIdProvider(this.databaseIdProvider);
            }

            if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
                sessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
            }

            if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
                sessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
            }

            if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
                sessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
            }

            return sessionFactoryBean.getObject();
        } catch (Exception var3) {
            log.error("{}", var3);
            return null;
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(this.mybatisSqlSessionFactory());
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DynamicDataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(this.dataSource);
        return dataSourceTransactionManager;
    }
}

