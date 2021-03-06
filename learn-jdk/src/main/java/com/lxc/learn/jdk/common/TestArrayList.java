package com.lxc.learn.jdk.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/12 9:09
 */
@Slf4j
public class TestArrayList {

    /**
     * Error ,线程不安全
     *
     * Vector是增删改查方法都加了synchronized，
     * 保证同步，但是每个方法执行的时候都要去获得锁，性能就会大大下降，
     * TODO ERROR 为什么get 方法也加了同步锁？
     *
     * Vector并非绝对安全,例子如下Vector-10
     *
     * 而CopyOnWriteArrayList 只是在增删改上加锁，
     * 但是读不加锁，在读方面的性能就好于Vector，CopyOnWriteArrayList支持读多写少的并发情况
     * 读写分离，写时复制出一个新的数组，完成插入、修改或者移除操作后将新数组赋值给array
     *
     * 这三个集合类都继承List接口
     *
     * 1、ArrayList是线程不安全的；
     * 2、Vector是比较古老的线程安全的，但性能不行；
     * 3、CopyOnWriteArrayList在兼顾了线程安全的同时，又提高了并发性，性能比Vector有不少提高
     */
    private static List<String> list = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (int i=0;i<10;i++){
                        list.add(String.valueOf(i));
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (String s : list){
                        System.out.println(s);
                    }
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Thread.sleep(3600*1000);
    }

    public void copyOnWriteArrayList(){
        CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();
        copyOnWriteArrayList.add("");
        copyOnWriteArrayList.get(0);
    }


    //Vector-10  //加锁处理可避免
    @Test
    public void test() {
        Vector<Integer> vector = new Vector<>();
        while (true) {
            for (int i = 0; i < 10; i++)
                vector.add(i);

            Thread removeThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++)
                        vector.remove(i);
                }
            });

            Thread printThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < vector.size(); i++)
                        System.out.print(vector.get(i));
                }
            });

            removeThread.start();
            printThread.start();

        }
    }


    @Test
    public void testVector(){
        Vector vector = new Vector();
        vector.add(0,"");
        vector.add(1,"");
        vector.add(2,null);
        vector.add(2,"");

        System.out.println(vector);


    }

    @Test
    public void testCopyOnWriteArrayList(){
        CopyOnWriteArrayList vector = new CopyOnWriteArrayList(new String[100]);
        vector.add(2,"");
        LinkedList linkedList = new LinkedList();
    }

    /**
     * ArrayList和LinkedList哪个更占空间?
     * 一般情况下，LinkedList的占用空间更大，因为每个节点要维护指向前后地址的两个节点，
     * 但也不是绝对，如果刚好数据量超过ArrayList默认的临时值时，ArrayList占用的空间也是不小的，
     * 因为扩容的原因会浪费将近原来数组一半的容量，不过，因为ArrayList的数组变量是用transient关键字修饰的，
     * 如果集合本身需要做序列化操作的话，ArrayList这部分多余的空间不会被序列化。
     */
}
