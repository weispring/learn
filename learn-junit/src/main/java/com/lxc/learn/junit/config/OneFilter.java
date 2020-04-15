package com.lxc.learn.junit.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ResponseFacade;
import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.catalina.util.ConcurrentDateFormat.GMT;

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

        HttpServletResponse response = (HttpServletResponse)servletResponse;
        response.setDateHeader("Date",0);

/*
        ResponseFacade facade = (ResponseFacade)servletResponse;
        facade.setDateHeader("Date",0);*/

    }

    @Override
    public void destroy() {

    }
}
