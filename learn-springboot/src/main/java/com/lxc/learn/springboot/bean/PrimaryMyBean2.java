package com.lxc.learn.springboot.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 *
 * 测试 注入时 首选 bean 的效果，多个bean时，带有@Primary的bean 会被注入
 * @author lixianchun
 * @Description
 * @date 2019/11/22 15:19
 */
@Component
@Primary
@Slf4j
public class PrimaryMyBean2 implements IPrimaryMyBean {


    public void test() {

    }

}
