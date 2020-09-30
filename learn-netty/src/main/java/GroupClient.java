import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author lixianchun
 * @description
 * @date 2020/9/28
 */

/**
 * 组播需要加入一个组播地址，需要特定端口来接收数据
 * 1.加入的组播ip不一样，是不能收到组播的
 * 2.加入的组播ip一样，组播port不一样，是不能收到组播的
 * 3.加入的组播ip一样，组播port一样，可以收到发送的组播数据，就算自己发送的数据也能收到
 */

public class GroupClient {
    private int MessageIndex = 0;
    private String ip = "224.0.0.1";//组播地址
    private int port = 6789;//指定数据接收端口
    private boolean closed = false;

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("UpdGroupClient start ");
                runClient();
            }
        }).start();
    }

    MulticastSocket socket = null;

    public void runClient() {
        try {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            InetAddress group = InetAddress.getByName(ip);
            socket = new MulticastSocket(port);
            socket.joinGroup(group);//加入组播地址
            while (!closed) {
                send();
                socket.receive(receivePacket);
                System.out.println("received packet from " + receivePacket.getAddress().getHostAddress() + " : " + receivePacket.getPort());
                String msg = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
                System.out.println("received " + msg);
                Thread.sleep(2000);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 组播发送数据
     */
    public void send() {
        String msg = "message from client," + MessageIndex++;
        try {
            byte[] message = msg.getBytes(); //发送信息
            InetAddress inetAddress = InetAddress.getByName(ip); //指定组播地址
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length, inetAddress, port); //发送数据包囊
            MulticastSocket multicastSocket = new MulticastSocket();//创建组播socket
            multicastSocket.send(datagramPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (MessageIndex >= 50) {
            closed = true;
        }
    }


    public static void main(String[] s) {
        GroupClient client = new GroupClient();
        client.start();
    }


}
