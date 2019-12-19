package com.lxc.learn.netty.nio;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/15 18:42
 * @Description:
 */
public class ClientA extends Client {
    public ClientA(String userName) {
        super(userName);
    }

    public static void main(String[] args) {
        new ClientA("ClientA").start();
    }
}
