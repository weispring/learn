package com.lxc.learn.junit.spring;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author lixianchun
 * @Description
 * @date 2019/10/24 10:04
 */
@Slf4j
@Data
public class BeanDefine implements InitializingBean,DisposableBean {


    /**
     * 创建spring condition ，并交给spring ioc 管理，可以通过ioc 获取
     */
    private String name;

    private Long id;



    @Override
    public void afterPropertiesSet() throws Exception {
        log.error("BeanDefine afterPropertiesSet :{}", "com.lxc.learn.junit.spring.BeanDefine");
        log.error("name:{},id:{}", name,id);
    }

    public void test(){
        log.error("test name:{},id:{}", name,id);
    }

    @Override
    public void destroy() {
        log.error("destroy：{}", "这是销毁方法");
    }

}
