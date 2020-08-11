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

            //插件
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
            //实体包
            if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
                sessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
            }

            if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
                sessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
            }

            //xml文件位置
            if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
                sessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
            }
           /** Configuration的配置，以及mapper 和 xml的解析
            * cache通过xml标签或者mapper上添加注解
            *
            * */
            return sessionFactoryBean.getObject();
        } catch (Exception var3) {
            log.error("{}", var3);
            return null;
        }
    }

    /**
     * 持有SqlSession的代理对象 new SqlSessionTemplate.SqlSessionInterceptor()
     * 通过代理创建真正的org.apache.ibatis.session.defaults.DefaultSqlSession
     * 最后通过DefaultSqlSession执行sql
     * @param sqlSessionFactory
     * @return
     */
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

    /** sessionFactoryBean.getObject()过程分析
     *1.注入的mapper,均为代理对象，MapperProxyFactory 创建的代理
     *
     * 2.解析创建MappedStatement
     * 解析实体包
     解析xml文件,包括解析 xml中方法解析为 MappedStatement

     创建mapperProxyFactory
     解析Mapper接口,包括解析 接口中注解为 MappedStatement
     解析mapper接口，CacheNamespace、CacheNamespaceRef和
     this.sqlAnnotationTypes.add(Select.class);
     this.sqlAnnotationTypes.add(Insert.class);
     this.sqlAnnotationTypes.add(Update.class);
     this.sqlAnnotationTypes.add(Delete.class);
     this.sqlProviderAnnotationTypes.add(SelectProvider.class);
     this.sqlProviderAnnotationTypes.add(InsertProvider.class);
     this.sqlProviderAnnotationTypes.add(UpdateProvider.class);
     this.sqlProviderAnnotationTypes.add(DeleteProvider.class);

     3.进行通用超类mapper方法解析，MappedStatement加入数组中

     4. mapper方法执行入口
     org.apache.ibatis.binding.MapperProxy#invoke,带有mapper方法的唯一标识 接口全名 + methodName
     //markI 此标识参数是如何加入的？
     最后通过 DefaultSqlSession执行，在 Configuration.mappedStatements 中找到 MappedStatement


     */
}

