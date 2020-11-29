package com.lxc.learn.intercom;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @Description
 * @Date 2020-11-29
 * @Created by lixianchun
 */


public class Server {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub

        ServerSocket server=null;
        Socket socket=null;
        BufferedReader br=null;
        BufferedWriter bw=null;
        BufferedReader bbr=null;
        //建立服务器，设置端点为5000
        server=new ServerSocket(5000);
        //提示用户服务器已经启动
        System.out.println("服务器已启动");
        //获取用户的输入 bbr
        bbr=new BufferedReader(new InputStreamReader(System.in));
        //监听客户端服务器
        socket=server.accept();//阻塞
        //当客户端启动，提示用户客户端已连接
        System.out.println("客户端已连接");
        //获取客户端的输入，存储到缓冲流中
        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //输出客户端的输出
        System.out.println("客户端说："+br.readLine());
        //获取用户的输入
        String line=bbr.readLine();
        while (!line.equals("over")) {
            //客户端
            bw.write(line);
            //换行
            bw.newLine();
            //刷新缓冲流
            bw.flush();
            //输出客户端的输入信息
            System.out.println("客户端说："+br.readLine());//阻塞
            //再获取用户的输入
            line=bbr.readLine();//阻塞

        }
        bw.close();
        br.close();
        bbr.close();
        socket.close();
    }

}

