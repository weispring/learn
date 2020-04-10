package com.lxc.learn.springboot.annotation;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @description
 * @date 2020/4/10
 */
@Slf4j
@RequestMapping("/annotation/scope")
@RestController
//多例注解需要注释
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeDependencyControl {

    /**
     * 2.多个Bean的依赖链中，有一个需要多例的问题
     */

    @Autowired
    private ScopeService scopeService;


    @RequestMapping("/testDependency")
    public Resp testDependency(@RequestParam("userName") String userName) throws InterruptedException {
        scopeService.setUserName(userName);

        int i=0;
        while (i < 10){
            log.info("线程：{}，用户名称：{}",Thread.currentThread().getId(),scopeService.getUserName());
            Thread.currentThread().sleep(1000);
            i++;
        }
        return RespUtil.success(this.scopeService.getUserName());
    }




}
