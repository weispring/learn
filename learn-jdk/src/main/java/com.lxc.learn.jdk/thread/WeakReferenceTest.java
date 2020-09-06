package com.lxc.learn.jdk.thread;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/21
 */
@Slf4j
public class WeakReferenceTest {

    /**
     * -verbose:gc -Xmx5m 打印gc日志和设置堆内存上限
     *
     * WeakReference(T referent, ReferenceQueue<? super T> q)：
     * 构造多了个ReferenceQueue，在对象被回收后，会把弱引用对象，也就是WeakReference对象或者其子类的对象，
     * 放入队列ReferenceQueue中，注意不是被弱引用的对象，被弱引用的对象（java.lang.ref.Reference#referent.referent）已经被回收了。

     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WeakReference<Object> weakReference=new WeakReference<Object>(new Object());
        System.out.println("beforeGC:"+weakReference.get());
        List<Byte[]> list=new ArrayList<>();
        for(int i=0;i<500;i++){
            list.add(new Byte[1024]);
        }
        Thread.sleep(3000);
        System.out.println("afterGC:"+weakReference.get());
    }
}
