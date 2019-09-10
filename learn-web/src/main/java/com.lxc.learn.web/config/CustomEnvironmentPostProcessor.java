package com.lxc.learn.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/9 9:43
 */
@Slf4j
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {
    private static boolean init = false;
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<PropertySourceLoader> propertySourceLoaders;


    public CustomEnvironmentPostProcessor() {
        log.info("初始化：{}",this.getClass().getName());
        if (!init) {
            //关键的一步，他也是配置在spring.factories 中
            // 实例化 factoryClass 参数对应的接口实现类
            this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, this.getClass().getClassLoader());
        }
    }

    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
      /*  log.info("postProcessEnvironment{}", System.currentTimeMillis());
        if (!init) {
            try {
                Iterator var3 = this.propertySourceLoaders.iterator();

                while(true) {
                    PropertySourceLoader propertySourceLoader;
                    do {
                        if (!var3.hasNext()) {
                            init = true;
                            return;
                        }
                        propertySourceLoader = (PropertySourceLoader)var3.next();
                    } while(!(propertySourceLoader instanceof YamlPropertySourceLoader));

                    Resource[] resources = this.resourcePatternResolver.getResources("classpath*:/config-*.yml");
                    Resource[] var6 = resources;
                    int var7 = resources.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Resource resource = var6[var8];
                       log.info("propertySources:{}",resource.getFilename());
                        List<PropertySource<?>> propertySources = propertySourceLoader.load(resource.getFilename(), resource);
                        if (propertySources != null && propertySources.size() > 0) {
                            Iterator var11 = propertySources.iterator();

                            while(var11.hasNext()) {
                                PropertySource<?> propertySource = (PropertySource)var11.next();
                                environment.getPropertySources().addLast(propertySource);
                            }
                        }
                    }
                }
            } catch (IOException var13) {
                log.error("加载配置文件失败:{}", var13.getMessage());
            }
        }*/
    }

    public int getOrder() {
            return -2147483637;
    }
}
