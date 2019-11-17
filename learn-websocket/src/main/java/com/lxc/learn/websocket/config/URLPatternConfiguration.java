package com.lxc.learn.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/30 15:26
 */
//@Configuration
public class URLPatternConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }
}
