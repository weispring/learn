package com.lxc.learn.redis;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/1 22:00
 * @Description:
 */
@Slf4j
public class MainTest {

    private static Socket socket = null;

    static {
        try {
            socket = new Socket("47.104.93.125",6379);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
    }

    public static void main(String[] args) throws Exception{
        auth("123456");
        String key = "k00";
        //客户端发送命令的格式
        /**
         *  *n crlf 分为n部分
         *         $m crlf 第一个参数长度
         *         p crlf  第一个参数
         *
         *         $mx crlf 第x个参数长度
         *         px crlf  第x个参数
         *         // 此格式即为aof文件格式
         */
        StringBuffer command = new StringBuffer();
        command.append("*").append("2").append("\r\n")
                .append("$").append("get".getBytes().length).append("\r\n")
                .append("get").append("\r\n")
                .append("$").append(key.getBytes().length).append("\r\n")
                .append(key).append("\r\n");
        log.info("{}",command.toString());
        socket.getOutputStream().write(command.toString().getBytes());
        byte[] bytes = new byte[8192];
        socket.getInputStream().read(bytes);
        log.info("{}",new String(bytes));
    }



    public static void auth(String password){
        try{
            StringBuffer command = new StringBuffer();
            command.append("*").append("2").append("\r\n")
                    .append("$").append("auth".getBytes().length).append("\r\n")
                    .append("auth").append("\r\n")
                    .append("$").append(password.getBytes().length).append("\r\n")
                    .append(password).append("\r\n");
            socket.getOutputStream().write(command.toString().getBytes());
            byte[] bytes = new byte[8192];
            socket.getInputStream().read(bytes);
            log.info("{}",new String(bytes));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }

    }
}
