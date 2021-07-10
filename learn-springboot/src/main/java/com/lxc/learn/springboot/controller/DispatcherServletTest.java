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
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.LastModified;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AnnotatedElement;

/**
 * DispatcherServlet
 *
 * 通过RequestMappingHandlerMapping获取handleMethod,
 * ...
 * 进入CglibAopProxy(还有jdk代理),然后执行通过反射执行真实的方法。
 *
 * 重要对象，保存path和handlerMethod信息，也是注册(register方法)到这里面。
 * org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.MappingRegistry
 * T为RequestMappingInfo，通过urlLookup找到mappingLookup，再找到handleMethod
 * private final Map<T, HandlerMethod> mappingLookup = new LinkedHashMap();
 * private final MultiValueMap<String, T> urlLookup = new LinkedMultiValueMap();
 *
 * 获取 HandlerExecutionChain
 * RequestMappingHandlerMapping
 org.springframework.web.servlet.handler.AbstractHandlerMapping#getHandler
 org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#getHandlerInternal

 *
 * endPoint走的是相似流程
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
    public Resp header1(HttpServletRequest request, HttpServletResponse response) throws InterruptedException {
        lastModified = System.currentTimeMillis();
        log.info("{}：{}",this.getClass().getName(),lastModified);
        //如果服务端 Response Header 设置了Keep-Alive:timeout={timeout}，客户端会就会保持此连接 timeout（单位秒）时间，超时之后关闭连接。
        //还有一种方式是接收端通在 Response Header 中增加Connection close标识，来主动告诉发送端，连接已经断开了，不能再复用了；客户端接收到此标示后，会销毁连接，再次请求时会重新建立连接。
      /*  response.setHeader("Connection", "Keep-Alive");
        response.setHeader("Keep-Alive", "max=5, timeout=120");*/
        Thread.sleep(5000);
        return RespUtil.convertResult(true);
    }

    /**
     * DispatcherServlet分发请求、进入CglibAopProxy
     * org.springframework.web.method.support.InvocableHandlerMethod#doInvoke
     org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept

     */

    //DispatcherServlet

}
