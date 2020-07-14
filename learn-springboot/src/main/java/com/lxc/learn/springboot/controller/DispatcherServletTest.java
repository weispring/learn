package com.lxc.learn.springboot.controller;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.springboot.aop.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.LastModified;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AnnotatedElement;

/**
 * @author lixianchun
 * @description
 * @date 2020/6/18
 */
@Slf4j
@Component
@RequestMapping
@ResponseBody
public class DispatcherServletTest implements LastModified{

    private volatile Long lastModified;

    @Override
    public long getLastModified(HttpServletRequest httpServletRequest) {
        return lastModified;
    }


    /**
     * RequestMappingHandlerAdapter.getLastModified() 固定返回-1
     * 因此不生效，并且不会调用 当前的DispatcherServletTest.getLastModified 方法
     * @param request
     * @param response
     * @return
     */

    @ServiceLog
    @RequestMapping(value = "/dispatcherServletLastModified")
    public Resp header1(HttpServletRequest request, HttpServletResponse response){
        lastModified = System.currentTimeMillis();
        log.info("{}：{}",this.getClass().getName(),lastModified);
        return RespUtil.convertResult(true);
    }

}
