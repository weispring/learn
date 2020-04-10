package com.lxc.learn.springboot.annotation;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
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
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ScopeControl {

    /**
     * 1.解决成员变量线程不安全问题，如this.userName
     *
     * 2.多个Bean的依赖链中，有一个需要多例的问题
     * https://www.jianshu.com/p/54b0711a8ec8
     */

    private String userName;


    @RequestMapping("/testThreadSafe")
    public Resp testThreadSafe(@RequestParam("userName") String userName) throws InterruptedException {
        this.userName = userName;
        int i=0;
        while (i < 10){
            log.info("线程：{}，用户名称：{}",Thread.currentThread().getId(),this.userName);
            Thread.currentThread().sleep(1000);
            i++;
        }
        return RespUtil.success(this.userName);
    }




}
