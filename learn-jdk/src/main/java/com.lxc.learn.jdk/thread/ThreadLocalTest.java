package com.lxc.learn.jdk.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/26 15:46
 */
@Slf4j
public class ThreadLocalTest {

    private static InheritableThreadLocal<Object> threadLocal = new InheritableThreadLocal<>();

    private static ReferenceQueue referenceQueue = new ReferenceQueue();
    /**
     * ThreadLocalMap 初始容量16，阈值16*2/3，hash 冲突时线性探索+1,map中元素继承WeakReference
     * @throws InterruptedException
     */
    @Test
    public void testThreadLocal() throws InterruptedException {
        threadLocal.set("test");
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("线程变量:{}",threadLocal.get());
            }
        }).start();
        Thread.sleep(3*1000);
        threadLocal.remove();
    }

    public static class CustomEntry extends WeakReference<Object> {
        private Object value;

        public CustomEntry(Object key, Object value){
            super(key, referenceQueue);
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        WeakReference<Object> weakReference=new CustomEntry(100000L, 1033333L);
        System.out.println("beforeGC:"+weakReference.get());
        List<Byte[]> list=new ArrayList<>();
        for(int i=0;i<600;i++){
            list.add(new Byte[1024]);
        }
        Thread.sleep(3000);
        System.out.println("afterGC:"+weakReference.get());
    }

    static {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true){
                    //此时 o 中referent 为null,已经被回收了
                    CustomEntry o = (CustomEntry) referenceQueue.poll();
                    if (o == null){
                        Thread.sleep(1000);
                    }
                    if (o != null){
                        log.info(">> {} ",o);
                    }
                }
            }
        }).start();
    }
}
