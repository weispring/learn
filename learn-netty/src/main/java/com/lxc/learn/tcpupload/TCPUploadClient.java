package com.lxc.learn.tcpupload;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/19 16:27
 * @Description:
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;

public class TCPUploadClient {
    public static void main(String[] args) {
        HashSet hashSet = new HashSet(10);
        hashSet.add("");
        Socket socket=null;
        OutputStream outputStream=null;
        FileInputStream fileInputStream=null;
        InputStream inputStream=null;
        try {
            //创建与服务器通信的Socket
            socket=new Socket(InetAddress.getLocalHost(), 10086);
            //获取Socket输出流
            outputStream=socket.getOutputStream();
            //指定待上传文件
            fileInputStream=new FileInputStream("E:\\lixianchun.png");
            //上传文件至服务端
            byte[] b=new byte[1024*1];
            int len=0;
            while((len=fileInputStream.read(b))!=-1) {
                outputStream.write(b, 0, len);
            }
            //关闭客户端输出流
            socket.shutdownOutput();

            //读取服务端返回的消息
            inputStream=socket.getInputStream();
            byte[] buf=new byte[1024*1];
            int length=0;
            while((length=inputStream.read(buf))!=-1) {
                String string=new String(buf, 0, length);
                System.out.println(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileInputStream!=null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
