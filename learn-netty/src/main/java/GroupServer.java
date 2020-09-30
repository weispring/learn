import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author lixianchun
 * @description
 * @date 2020/9/28
 */

public class GroupServer {
    public boolean closed = false;
    public String ip = "224.0.0.1";//组播虚拟地址
    public int port = 6789;//组播Ip
    public int MessageIndex = 0;

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("UdpTestServer start ");
                runServer();
            }
        }).start();
    }

    private void runServer(){
        try {
            InetAddress group = InetAddress.getByName(ip);
            MulticastSocket s = new MulticastSocket(port);
            byte[] arb = new byte[1024];
            s.joinGroup(group);//加入该组
            while(!closed){
                DatagramPacket datagramPacket =new DatagramPacket(arb,arb.length);
                s.receive(datagramPacket);
                System.out.println("received packet from " + datagramPacket.getAddress().getHostAddress() + " : " + datagramPacket.getPort());
                System.out.println(new String(arb));
                Thread.sleep(2000);
                send();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UdpTestServer run Exception: "+e.toString());
        }
    }




    public void send(){
        try{
            String sendMessage="hello ,message from server,"+MessageIndex++;
            byte[] message = sendMessage.getBytes(); //发送信息
            InetAddress inetAddress = InetAddress.getByName(ip); //指定组播地址
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length, inetAddress, port); //发送数据包囊
            MulticastSocket multicastSocket = new MulticastSocket();//创建组播socket
            multicastSocket.send(datagramPacket);
        }catch (Exception e) {
            System.out.println("UdpTestServer send Exception: "+e.toString());
        }

        if(MessageIndex>=50){
            closed = true;
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        GroupServer server = new GroupServer();
        server.start();
    }

}
