package com.lxc.learn.temp;

import com.lxc.learn.temp.condition.AA;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lixianchun
 * @description
 * @date 2020/5/18
 */
@Slf4j
public class Main {

    public static void main(String[] args) {


        //org.springframework.scheduling.annotation.ProxyAsyncConfiguration
        //ProxyAsyncConfiguration AsyncExecutionInterceptor

        //AsyncAnnotationAdvisor
        //AsyncExecutionAspectSupport 執行線程池選擇

        System.out.println("------------");
        Class cla = deduceMainApplicationClass();

        System.out.println("------------");
    }

    @Test
    public void test(){
        deduceMainApplicationClass();
        find();
    }

    private static Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = (new RuntimeException()).getStackTrace();
            StackTraceElement[] var2 = stackTrace;
            int var3 = stackTrace.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                StackTraceElement stackTraceElement = var2[var4];
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        } catch (ClassNotFoundException var6) {
            ;
        }

        return null;
    }

    public void find(){
        AnnotatedClassFinder finder = new AnnotatedClassFinder(EnableAsync.class);
        Class c = finder.findFromClass(AA.class);

        System.out.println(c);

    }


}
