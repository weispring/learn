package com.lxc.learn.websocket.control;

import com.alibaba.fastjson.JSON;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.websocket.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
@Controller
@Slf4j
public class ChatController {

    @Autowired
    public SimpMessagingTemplate template;

    @MessageMapping("/chat.heart")
    public ChatMessage heart(@Payload ChatMessage chatMessage) {
        log.warn("heart : {} ", JsonUtil.objectToJson(chatMessage));
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @RequestMapping(value = "send2user")
    @ResponseBody
    public ChatMessage sendMq2User(@RequestBody ChatMessage chatMessage){
        log.info("{}发送{}：{}",chatMessage.getSender(),chatMessage.getTo(),chatMessage.getContent());
        //源码 org.springframework.messaging.simp.SimpMessagingTemplate.convertAndSendToUser(java.lang.String, java.lang.String, java.lang.Object, java.util.Map<java.lang.String,java.lang.Object>, org.springframework.messaging.core.MessagePostProcessor)

        template.convertAndSendToUser(chatMessage.getTo(), "/message", JSON.toJSONString(chatMessage));
        return chatMessage;
    }


}
