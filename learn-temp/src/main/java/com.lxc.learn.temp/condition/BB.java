package com.lxc.learn.temp.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/14
 */
@Slf4j
@ConditionalOnClass(AA.class)
@ConditionalOnBean(AA.class)
@ConditionalOnProperty(prefix = "condition.property",name = "name",havingValue = "true")
@Configuration
public class BB {


    public BB(){
        //log.info("this is " + this.getClass().getName());
    }

    /**
     *
     public interface Condition {
     // ConditionContext内部会存储Spring容器、应用程序环境信息、资源加载器、类加载器
     boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
     }

     Condition接口有个实现抽象类SpringBootCondition，SpringBoot中所有条件注解对应的条件类都继承这个抽象类。它实现了matches方法：
     */

    public static void main(String[] args) {
        String a = "i love";
        String b = "i love";
        String c = new String("i love");
        String d = new String("i love");
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
        new HashMap<>().put("","");

        String key = "abc";
        int h = 0;
        h = (h = key.hashCode()) ^ (h >>> 16);
        System.out.println(Integer.toBinaryString(key.hashCode()>>>16));
        System.out.println(Integer.toBinaryString(key.hashCode()));
        System.out.println(Integer.toBinaryString(h));
    }


}
