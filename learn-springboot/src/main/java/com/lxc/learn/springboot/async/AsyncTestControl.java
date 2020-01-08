package com.lxc.learn.springboot.async;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/31
 */
@RestController
@Slf4j
public class AsyncTestControl {

    @Autowired
    private AsyncLog asyncLog;
    //@Autowired
    private SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;

    @RequestMapping("/testAsync")
    public Resp testAsync(HttpServletRequest httpServletRequest){
        log.info("入参：{}",httpServletRequest.getHeaderNames());
        Integer a = Integer.valueOf(httpServletRequest.getParameter("times"));
        for (int i=0;i<a;i++){
            asyncLog.printLog(Long.valueOf(httpServletRequest.getParameter("time")));
        }
        return RespUtil.success();

    }

}
