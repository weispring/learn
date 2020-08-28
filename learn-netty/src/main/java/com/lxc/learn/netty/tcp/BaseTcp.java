package com.lxc.learn.netty.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/14
 */
@Slf4j
public class BaseTcp {

    public static void startClient(String host, int port, String input) {
        try  {
            // 第一步：创建服务端连接
            Socket socket = new Socket(host, port);
            // 第二步：创建输出流
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
            // 第三步：创建输入流
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            printWriter.println(input);
            printWriter.flush();

            //设置为Connection: close后，无法二次发送请求
            printWriter.println(input);
            printWriter.flush();

            String line;
            log.info("收到响应内容：");
            while ((line = bufferedReader.readLine()) != null){
                log.info(line);
            }
            //todo markp 下面代码无法发送
            printWriter.println(input);
            printWriter.flush();
            log.info("收到响应内容：");
            while ((line = bufferedReader.readLine()) != null){
                log.info(line);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
