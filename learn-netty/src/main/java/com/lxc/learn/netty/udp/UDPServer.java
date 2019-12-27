package com.lxc.learn.netty.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP服务端
 * 
 * @author liyulin
 * @date 2013/03/08
 */
@Slf4j
public class UDPServer {

	public static void main(String[] args) {
		try (DatagramSocket dsocket = new DatagramSocket(5001);
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
			log.info("服务端正在等待输入数据....");
			InetAddress address = InetAddress.getByName("localhost");
			while (true) {
				log.info("阻塞：");
				String str = reader.readLine();
				log.info("阻塞1：");
				byte[] buffer = str.getBytes();
				dsocket.send(new DatagramPacket(buffer, buffer.length, address, 5000));
				if ((str == null) || (str.equals("end"))) {
					break;
				}
			}
		} catch (IOException e) {
			log.error("IOException", e);
		}
	}

}