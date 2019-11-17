package com.lxc.learn.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket的配置
 * @author lixianchun
 * @Description
 * @date 2019/7/29 19:53
 */

@Configuration
public class WebSocketStompConfig{
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }
}
