package com.lxc.learn.common.test.jgroups;
import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import org.jgroups.*;
import org.jgroups.protocols.UDP;
import org.jgroups.util.Util;
/**
 * @author lixianchun
 * @Description
 * @date 2019/8/8 9:50
 */

@Slf4j
public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name = "ABC";
    //消息
    private List<String> state = new LinkedList<String>();
    List<Address> list;
    private void start() throws Exception {
        channel = new JChannel("com/lxc/learn/common/test/jgroups/kubernetes.xml"); //使用默认配置udp.xml
        channel.setReceiver(this); //指定Receiver用来收消息和得到View改变的通知
        channel.connect("umall-dev"); //连接到集群

        //刚加入集群时，我们通过getState()获取聊天历史记录
        //getState()的第一个参数代表目的地地址，这里传null代表第一个实例（coordinator）
        //第二个参数代表等待超时时间，我们等待10秒。如果时间到了，State传递不过来，会抛例外。也可以传0代表永远等下去
        channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.print("> ");
                System.out.flush();
                String line = in.readLine().toLowerCase();
                if (line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                line = "[" + user_name + "] " + line;

                //Message构造函数的第一个参数代表目的地地址，这里传null代表要发消息给集群内的所有地址
                //第二个参数表示源地址，传null即可，框架会自动赋值
                //第三个参数line会被序列化成byte[]然后发送，推荐自己序列化而不是用java自带的序列化
                Address address = channel.getAddress();
                String a = channel.getAddressAsString();
                log.info("address:{},a:{},class:{}", address, a,address.getClass().getName());
                Address dest = list.get(new Random().nextInt(list.size()));
                Message msg = new Message(dest, line);
                channel.send(msg); //发消息到集群

            } catch (Exception e) {
            }
        }
    }

    @Override
    //每当有实例加入或者离开集群(或崩溃)的时候，viewAccepted方法会被调用
    public void viewAccepted(View new_view) {
        log.info("** view: 加入或者离开{}" + new_view);
        list = new_view.getMembers();
        log.info("所有地址：{}" + list);
    }

    @Override
    //有消息时，byte[]会被反序列化成Message对象，也可以用Message.getBuffer得到byte[]然后自己反序列化。
    public void receive(Message msg) {
        String line = msg.getSrc() + ": " + msg.getObject();
        log.info("接受到消息：{}", line);
        //加入到历史记录
        synchronized (state) {
            state.add(line);
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        //当JChannel.getState()被调用时，某个原来就在集群中的实例的getState会被调用用来得到集群的共享state
        //Util.objectToStream方法将state序列化为output二进制流
        synchronized (state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        //当以上集群的共享state被得到后，新加入集群的实例的setState方法就会被调用了
        List<String> list = (List<String>) Util.objectFromStream(new DataInputStream(input));
        synchronized (state) {
            state.clear();
            state.addAll(list);
        }
       log.info("messages in chat history:{},{}",list.size() ,list);
    }

    public static void main(String[] args) throws Exception {
        URL url = SimpleChat.class.getClassLoader().getResource("kubernetes.xml");
        log.info("{}", url.getPath());
        //new SimpleChat().start();
    }

}
