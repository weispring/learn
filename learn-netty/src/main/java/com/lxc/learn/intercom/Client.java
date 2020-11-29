package com.lxc.learn.intercom;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * @Description
 * @Date 2020-11-29
 * @Created by lixianchun
 */


public class Client {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Socket socket=null;
        BufferedReader br=null;
        BufferedWriter bw=null;
        BufferedReader bbr=null;

        try {
            //获取服务器连接
            socket=new Socket("localhost",5000);
            //获取用户输入
            bbr=new BufferedReader(new InputStreamReader(System.in));
            //读取输入
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //输出流
            bw=new BufferedWriter(new  OutputStreamWriter(socket.getOutputStream()));
            //定义一个数组，获取用户输入的数据流
            String line=bbr.readLine();
            //循环，
            //判断如果用户输入的是over，则跳出循环，否则循环
            while (!line.equals("over")) {
                //输出用户的输入
                bw.write(line);
                bw.newLine();
                //刷新缓冲数据
                bw.flush();
                //再次读取服务器数据
                System.out.println("服务器说： "+br.readLine());
                //再次赋值给到line
                line=bbr.readLine();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                bw.close();
                br.close();
                bbr.close();
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

}

