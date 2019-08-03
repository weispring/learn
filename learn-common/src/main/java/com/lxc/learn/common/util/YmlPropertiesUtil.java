package com.lxc.learn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/6/29 14:04
 */
@Component
@Slf4j
public class YmlPropertiesUtil {

    @Autowired
    private Environment environment;

    @PostConstruct
    public void getAll(){
        try{
            StandardServletEnvironment servletEnvironment = (StandardServletEnvironment) environment;
            Iterator<PropertySource<?>> iterator = servletEnvironment.getPropertySources().iterator();
            while (iterator.hasNext()){
                PropertySource<?> propertySource = iterator.next();
                String ymlName = propertySource.getName();
                log.info("yml:{}", ymlName);
                if (ymlName.contains("application")){
                    Object object = propertySource.getSource();
                    if (object instanceof Map){
                        Map map = (Map) object;
                        Iterator keys = map.keySet().iterator();
                        while (keys.hasNext()){
                            String key = (String) keys.next();
                            log.info("key:{},value:{}", key, propertySource.getProperty(key));
                        }
                    }
                }
            }
        }catch (Exception e){

        }

        log.info("key:{}={}", environment.getProperty(""));
    }
}
