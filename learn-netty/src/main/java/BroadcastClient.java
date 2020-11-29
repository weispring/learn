import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/27
 */
@Slf4j
public class BroadcastClient {
    private int MessageIndex = 0;
    private int port = 6787;//数据监听绑定端口
    private boolean closed = false;


    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("BroadcastClient start ");
                runClient();
            }
        }).start();
    }

    DatagramSocket socket = null;

    private void runClient() {
        try {
            byte[] receiveBuffer = new byte[1024];//数据缓冲区
            socket = new DatagramSocket(port);//绑定端口进行数据监听
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);//数据接收包囊
            while (!closed) {
                count();//计算数量，超过五十次关闭连接
                socket.receive(receivePacket);//接收数据
                System.out.println("received packet from " + receivePacket.getAddress().getHostAddress() + " : " + receivePacket.getPort());
                String msg = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
                System.out.println("received " + msg);
                //回复
                send(receivePacket);
                Thread.sleep(2000);
            }
            System.out.println("socket close ");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void count() {
        MessageIndex++;
        if (MessageIndex >= 50) {
            closed = true;
        }
    }


    public static void main(String[] s) {
        BroadcastClient client = new BroadcastClient();
        client.start();
    }


    private static void send(DatagramPacket receivePacket) {
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
            String msg = "确认收到";
            DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                    receivePacket.getAddress(), receivePacket.getPort());
            ds.send(dp);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }finally {
            ds.close();
        }
    }

}





