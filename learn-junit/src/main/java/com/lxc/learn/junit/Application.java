package com.lxc.learn.junit;

import com.lxc.learn.common.web.SetCharacterEncodingFilter;
import com.lxc.learn.junit.config.ReadConfigFile;
import com.lxc.learn.junit.test.ConfigProperty;
import com.lxc.learn.junit.util.ClassUtil;
import com.mysql.jdbc.Driver;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.StandardServletEnvironment;

import javax.validation.BootstrapConfiguration;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.Map;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:44
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lxc.learn"})
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
@MapperScan("com.lxc.learn.junit.mapper*")
@Slf4j
//读取当前项目的其他yml配置，其依赖的jar好像不能读取
//@org.springframework.context.annotation.PropertySource({"classpath:config-common.yml","classpath:config-web.yml"})
public class Application {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class,args);
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        while (iterator.hasNext()){
            Object o = context.getBeanFactory().getBean(iterator.next());
            if (o instanceof ConfigProperty){
                log.error("---------"+o.toString());
            }
            //log.error("----{}----", iterator.next());
        }
        Map map = ClassUtil.getPermissions("com.lxc.learn.junit.control",true);
        log.info("");

        System.out.println(Runtime.getRuntime().totalMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().freeMemory()/1024/1024);
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);

     /*   try {
            Runtime.getRuntime().exec(" taskkill /pid " + processID() +" /f");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //System.exit(0);
        //SetCharacterEncodingFilter
    }

    public static String processID()
    {
        /*
        java.lang.management.RuntimeMXBean是Java 虚拟机的运行时系统的管理接口。
        使用它可以获取正在运行的 Java 虚拟机等信息，包括获取PID。

         * runtimeMXBean.getName()取得的值包括两个部分：PID和hostname，两者用@连接。
         */
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name = runtimeMXBean.getName();
        System.out.println("ManagementFactory.getRuntimeMXBean().getName() : " + name);
        return name.split("@")[0];
    }

}
