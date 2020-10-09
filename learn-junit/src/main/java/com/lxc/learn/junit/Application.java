package com.lxc.learn.junit;

import com.lxc.learn.common.util.EmailService;
import com.lxc.learn.common.util.SpringContextHolder;
import com.lxc.learn.junit.classPathFile.ReadFile;
import com.lxc.learn.junit.test.ConfigProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import java.util.Iterator;


/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:44
 * @Description:
 */
@SpringBootApplication(scanBasePackages = {"com.lxc.learn"})
@Import(SchedulingConfiguration.class)
@EnableTransactionManagement
@EnableAsync
@MapperScan("com.lxc.learn.junit.mapper*")
@Slf4j
//读取当前项目的其他yml配置，其依赖的jar好像不能读取
//@org.springframework.context.annotation.PropertySource({"classpath:config-common.yml","classpath:config-web.yml"})
public class Application {

    // jvm 参数
    //-XX:+PrintGCDetails -Xms100M -Xmx200M -Xmn100M -XX:SurvivorRatio=8
    //-XX:+UseAdaptiveSizePolicy 开启后，就不需要手工指定新生代的大小（-Xmn）、
    // Eden与Survivor区的比例（-XX：SurvivorRatio）、 晋升老年代对象年龄（-XX： PretenureSizeThreshold）等细节参数

    //即使指定了-XX:+UseAdaptiveSizePolicy ，-Xmn100M也会生效
    public static void main(String[] args) throws InterruptedException {
        //System.setProperty("spring.devtools.restart.enabled","true");
        ReadFile.main(null);
        ConfigurableApplicationContext context = null;
        try {
            context = SpringApplication.run(Application.class, args);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        Iterator<String> iterator = context.getBeanFactory().getBeanNamesIterator();
        while (iterator.hasNext()) {
            Object o = context.getBeanFactory().getBean(iterator.next());
            if (o instanceof ConfigProperty) {
                log.error("---------" + o.toString());
            }
            //log.error("----{}----", iterator.next());
        }
        EmailService emailService = context.getBean(EmailService.class);
        emailService = SpringContextHolder.getBean(EmailService.class);
        System.out.println(emailService);
        //RequestContextFilter
        //SpringBootCondition
       // DefaultAdvisorAutoProxyCreator
        //PlatformTransactionManager
        ReadFile.main(null);
        //ConnectionWatchdo
        //DefaultSqlSessionFactory

        //restart atom
        //org.springframework.boot.devtools.autoconfigure
        //AnnotationConfigServletWebServerApplicationContext

        //HealthEndpoint
        log.info("{}","測試");
    }



    /**
     * it's for set http url auto change to https
     */
    // spring boot 2.x
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }

}
