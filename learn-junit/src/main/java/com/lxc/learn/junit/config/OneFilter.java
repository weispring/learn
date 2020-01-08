package com.lxc.learn.junit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
@Slf4j
public class OneFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("start :{}",this.getClass().getName());
        filterChain.doFilter(servletRequest,servletResponse);
        log.info("end :{}",this.getClass().getName());
    }

    @Override
    public void destroy() {

    }
}
