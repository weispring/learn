package com.lxc.learn.junit.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lixianchun
 * @description
 * @date 2020/10/19
 */

@Slf4j
//@Configuration
public class ServletConfig {


    /**
     * spring 配置多个 Servlet 需要配置路由策略
     * 默认路由  Servlet dispatcherServlet mapped to [/]
     *
     * 配置方法如下：
     * 1.@WebServlet、@ServletComponentScan(basePackages = {"com.lxc.learn.junit"})
     * 2.配置类的方式
     * @return
     */
    //@Bean
    public ServletRegistrationBean heServletRegisterBen() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new TimeServlet(), "/timeServlet"
        );
        return servletRegistrationBean;
    }
}