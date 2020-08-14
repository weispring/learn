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
		try {// 第一步：创建监听端口
			/**
			 * 通信的时候，双方必须知道对方的标识，好比发邮件必须知道对方的邮件地址。互联网上每个计算机的唯一标识就是IP地址，类似123.123.123.123。如果一台计算机同时接入到两个或更多的网络，比如路由器，它就会有两个或多个IP地址，所以，IP地址对应的实际上是计算机的网络接口，通常是网卡。
			 *
			 * 我们要绑定监听的地址和端口。服务器可能有多块网卡，可以绑定到某一块网卡的IP地址上，也可以用0.0.0.0绑定到所有的网络地址，
			 * 还可以用127.0.0.1绑定到本机地址。
			 * 127.0.0.1是一个特殊的IP地址，表示本机地址，如果绑定到这个地址，客户端必须同时在本机运行才能连接，
			 * 也就是说，外部的计算机无法连接进来。我们要绑定监听的地址和端口。服务器可能有多块网卡，可以绑定到某一块网卡的IP地址上，
			 * 也可以用0.0.0.0绑定到所有的网络地址，还可以用127.0.0.1绑定到本机地址。127.0.0.1是一个特殊的IP地址，表示本机地址，
			 * 如果绑定到这个地址，客户端必须同时在本机运行才能连接，也就是说，外部的计算机无法连接进来。
			 */
			ServerSocket serverSocket = new ServerSocket(1200);
			while (true) {
				// 第二步：接受请求
				Socket socket = serverSocket.accept();
				//但是服务器还需要同时响应多个客户端的请求，所以，每个连接都需要一个新的进程或者新的线程来处理，否则，服务器一次就只能服务一个客户端了。
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							// 第三步：创建信息输入流，接受客户端信息
							BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
							// 第四步：创建输出流，向客户端输出信息,cmd 默认编码GBK
							PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "GBK"));
							String str = bufferedReader.readLine();
							log.info("接受：{}",str);
							printWriter.println("服务端已接收到信息：" + str);
							printWriter.flush();
						}catch (Exception e){
							log.error(e.getMessage(), e);
						}
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TCPServer().startServer();
	}
	
}
