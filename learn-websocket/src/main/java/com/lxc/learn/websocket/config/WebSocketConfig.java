package com.lxc.learn.websocket.config;

import com.lxc.learn.common.util.web.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.*;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();

        registry.setApplicationDestinationPrefixes("/app");
        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息,topic用来广播，user用来实现p2p
        registry.enableSimpleBroker("/topic","/user").setHeartbeatValue(new long[]{5000, 5000}).setTaskScheduler(te);   // Enables a simple in-memory broker
        registry.setUserDestinationPrefix("/user");//此处user需要一致

        //   Use this for enabling a Full featured broker like RabbitMQ

        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }


    /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(userInterceptor());
    }

    /**
     * @return
     * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     */
    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }


    @Slf4j
    public static class UserInterceptor implements ChannelInterceptor {

        /**
         * 获取包含在stomp中的用户信息
         */

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
            StompCommand command = accessor.getCommand();
            if (StompCommand.CONNECT.equals(command)) {
              /*  String token = accessor.getNativeHeader("token").get(0);
                String channel = accessor.getNativeHeader("channel").get(0);
                String clientType = accessor.getNativeHeader("clientType").get(0);
                // 设置当前访问器的认证用户
                String user = clientType + "-" + channel;
                accessor.setUser(new User(user));
                log.info(MessageFormat.format("用户{0}的WebSocket连接已经建立...", user));
                if (clientType.equals("1")) {
                } else if (clientType.equals("2")) {

                }*/
            }
            // 用户已经断开连接
            if (StompCommand.DISCONNECT.equals(command)) {
                String user = "";
                Principal principal = accessor.getUser();
                if (principal != null && StringUtil.isNotEmpty(principal.getName())) {
                    user = principal.getName();
                    log.info(MessageFormat.format("房間{0}的WebSocket连接已经断开...", user));
                }
            }
            return message;
        }
    }

    public static class User implements Principal {
        private final String name;

        public User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

}
