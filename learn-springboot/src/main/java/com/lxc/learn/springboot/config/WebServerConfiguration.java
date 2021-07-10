package com.lxc.learn.springboot.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/7/10
 **/
@Configuration
public class WebServerConfiguration implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        //使用对应工厂类提供给我们的接口定制化我们的tomcat connector
        ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
                //定制KeepAliveTimeout，设置10秒内没有请求则服务器自动断开keepalive连接
                protocol.setKeepAliveTimeout(10000);
                //当客户端发送超过5个请求则自动断开keepalive连接
                protocol.setMaxKeepAliveRequests(Integer.MAX_VALUE);
            }
        });
    }
}