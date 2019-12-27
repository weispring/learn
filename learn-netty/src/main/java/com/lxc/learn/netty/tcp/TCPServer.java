package com.lxc.learn.netty.tcp;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP服务端
 * 
 * @author liyulin
 * @date 2013/03/08
 */
@Slf4j
public class TCPServer {

	public void startServer() {
		try (// 第一步：创建监听端口
			 ServerSocket serverSocket = new ServerSocket(1200);
			 // 第二步：接受请求
			 Socket socket = serverSocket.accept();
			 // 第三步：创建信息输入流，接受客户端信息
			 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			 // 第四步：创建输出流，向客户端输出信息
			 PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));) {

			String str = bufferedReader.readLine();
			log.info("接受：{}",str);
			printWriter.println("服务端已接收到信息：" + str);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TCPServer().startServer();
	}
	
}
