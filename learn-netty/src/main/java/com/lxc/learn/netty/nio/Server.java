package com.lxc.learn.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @author KronChan
 * @date 2019-04-07 22:08
 */
public class Server {
    public static void main(String[] args) throws IOException {
        final int port = 8001;
        // 1. 打开 ServerSocketChannel 用来监听客户端连接请求
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port));
        // 2. 设置非祖塞模式
        channel.configureBlocking(false);
        Selector selector = Selector.open();
        // 3. 将 ServerSocketChannel 注册到 Selector 上，并且监听 ACCEPT 事件
        channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动");

        // 缓冲区，设置大小 1KB
        final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
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
                // 6. 判断是否有可以接受的连接，有的话创建连接
                if (selectionKey.isAcceptable()) {
                    handleAccept(selector, selectionKey);
                }
                // 8. 判断该消息是否可以读
                if (selectionKey.isReadable()) {
                    handleRead(selector,byteBuffer, selectionKey);
                }
            }
        }
    }

    private static void handleRead(Selector selector,ByteBuffer byteBuffer, SelectionKey selectionKey) throws IOException {
        //9. 开始读取数据
        SocketChannel readChannel = (SocketChannel) selectionKey.channel();
        byteBuffer.clear();
        while (readChannel.read(byteBuffer) > 0) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                // 解码
             /*   byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);*/
                String message =   Charset.forName("UTF-8").decode(byteBuffer).toString();
                System.out.println("receive : " + message);
                //TODO ERROR 第三个参数为string 不行？
                broadCast(selector,readChannel,byteBuffer);
            }
        }
    }

    private static void handleAccept(Selector selector, SelectionKey selectionKey) throws IOException {
        ServerSocketChannel acceptChannel = (ServerSocketChannel) selectionKey.channel();
        // 7. 创建客户端连接连接，并且设置非阻塞模式，将连接注册到 Selector 上，监听读操作，
        SocketChannel acceptSocket = acceptChannel.accept();
        acceptSocket.configureBlocking(false);
        acceptSocket.register(selector, SelectionKey.OP_READ);
        //TODO ERROR 以下写出无效？
      /*  ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("服务器回应".getBytes());
        acceptSocket.write(byteBuffer);*/
        acceptSocket.write( Charset.forName("UTF-8")
                .encode("服务器回应"));
    }

    private static void broadCast(Selector selector,SocketChannel source, ByteBuffer request){
        Set<SelectionKey> selectionKeys = selector.keys();
        Iterator iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey selectionKey = (SelectionKey) iterator.next();
            SelectableChannel channel =  selectionKey.channel();
            if (channel instanceof SocketChannel && channel != source){
                SocketChannel socketChannel = (SocketChannel) channel;
                try {
                    System.out.println("send : " );
                    socketChannel.write(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
