package com.lxc.learn.netty.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP客户端
 * 
 * @author liyulin
 * @date 2013/03/08
 */
@Slf4j
public class UDPClient {

	/**
	 * TCP是建立可靠连接(有序，重发，面向连接)，并且通信双方都可以以流的形式发送数据。相对TCP，UDP则是面向无连接的协议。
	 使用UDP协议时，不需要建立连接，只需要知道对方的IP地址和端口号，就可以直接发数据包。但是，能不能到达就不知道了。
	 虽然用UDP传输数据不可靠，但它的优点是和TCP比，速度快，对于不要求可靠到达的数据，就可以使用UDP协议。
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] buffer = new byte[1024];
		try (DatagramSocket datagramSocket = new DatagramSocket(5000);) {
			log.info("客户端正在等待服务器端发送数据....");
			while (true) {
				DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
				datagramSocket.receive(datagramPacket);
				log.info("来自主机：{}，端口号：{}", datagramPacket.getAddress().toString(), datagramPacket.getPort());

				String psx = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
				log.info(psx);
				log.info(psx.length()+"");
				if ((psx == null) || (psx.equals("end"))) {
					break;
				}
			}
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}
	
}