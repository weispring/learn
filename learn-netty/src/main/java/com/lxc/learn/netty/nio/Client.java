package com.lxc.learn.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author KronChan
 * @date 2019-04-07 22:08
 */
public class Client {
    private int port = 8001;

    private int bytes = 1024;

    private String userName = "";

    public Client(String userName){
        this.userName = userName;
    }


    public void start(){
        try {
            SocketChannel socket = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
            socket.configureBlocking(false);
            //socket.connect(new InetSocketAddress("127.0.0.1", port));
            while (!socket.finishConnect()) {
                System.out.println("等待连接中，" + System.currentTimeMillis());
            }
            System.out.println("连接完成，等待写入" + System.currentTimeMillis());




            Selector selector = Selector.open();
            socket.register(selector,SelectionKey.OP_READ);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    receice(selector);
                }
            }).start();
            sendMessage(socket,"建立连接！！！");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String line = scanner.nextLine();
                System.out.println(line);
                sendMessage(socket,line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(SocketChannel socket, String msg) throws IOException {
        //ByteBuffer byteBuffer = ByteBuffer.allocate(bytes);

        socket.write(Charset.forName("UTF-8").encode(userName + ":" + msg));
        //byteBuffer.clear();
    }


    private static void receice(Selector selector) {
        try {
            while (true) {
                int select = selector.select();
                // 4. 判断是否就绪，如果什么消息也没有则什么也不做
                if (select == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                // 5. 如果有消息， 处理新的请求，
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (!selectionKey.isValid()) {
                        continue;
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel readChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        while (readChannel.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            while (byteBuffer.hasRemaining()) {
                                // 解码
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                String message = new String(bytes, StandardCharsets.UTF_8);
                                System.out.println("con resp : "+message);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
