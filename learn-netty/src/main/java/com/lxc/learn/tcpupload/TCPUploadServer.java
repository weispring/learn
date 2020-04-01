package com.lxc.learn.tcpupload;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/19 16:27
 * @Description:
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPUploadServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(10086);
        while(true) {
            Socket client=serverSocket.accept();
            HandleUploadRunnableImpl handleUploadRunnableImpl=new HandleUploadRunnableImpl(client);
            Thread thread=new Thread(handleUploadRunnableImpl);
            thread.start();
        }

    }

}

//处理上传文件的Runnable
class HandleUploadRunnableImpl implements Runnable{
    private Socket socket=null;
    private InputStream inputStream=null;
    private FileOutputStream fileOutputStream=null;
    private OutputStream outputStream=null;
    public HandleUploadRunnableImpl(Socket socket) {
        this.socket=socket;
    }
    @Override
    public void run() {
        //获取客户端ip
        String ip=socket.getInetAddress().getHostAddress();
        try {
            //指定保存上传图片的目录
            File uploadDir=new File("E:\\upload");
            if(!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            //指定文件保存的名称
            File file=new File(uploadDir,ip+".jpg");
            //读取客户端上传的文件并保存
            inputStream=socket.getInputStream();
            fileOutputStream=new FileOutputStream(file);
            byte[] b=new byte[1024*1];
            int len=0;
            while((len=inputStream.read(b))!=-1) {
                fileOutputStream.write(b, 0, len);
            }
            //向客服端返回消息
            outputStream = socket.getOutputStream();
            outputStream.write("上传完毕".getBytes());
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream!=null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

