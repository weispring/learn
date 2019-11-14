package com.lxc.learn.websocket.util;

/**
 * webSocket 客户端工具
 * @author lixianchun
 * @Description
 * @date 2019/7/30 16:32
 */

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;


@Slf4j
public class WebsocketClient {

    public static WebSocketClient client;

    public static void main(String[] args) {
       for (int i=0;i<10000;i++){
           send();
       }
    }

    public static void send(){
        String from = UUID.randomUUID().toString();
        try {
            client = new WebSocketClient(new URI("ws://localhost:9090/ws/"), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("握手成功");
                }

                @Override
                public void onMessage(String msg) {
                    log.info("收到消息=========={}", msg);
                    if (msg.equals("over")) {
                        client.close();
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("链接已关闭");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    log.info("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        log.info(client.getDraft().toString());
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            log.info("正在连接...");
        }
        //连接成功,发送信息
        JSONObject message = new JSONObject();

        message.put("content", "哈喽!!!!"+ UUID.randomUUID().toString());
        message.put("type", "CHAT");
        message.put("username","l");
        message.put("to", "z");
        client.send(message.toJSONString());
    }

}
 
 
