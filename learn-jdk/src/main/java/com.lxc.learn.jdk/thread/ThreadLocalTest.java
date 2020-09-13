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

    /**
     * 内存泄漏
     * 当一个线程调用ThreadLocal的set方法设置变量时候，当前线程的ThreadLocalMap里面就会存放一个记录，这个记录的key为ThreadLocal的引用，value则为设置的值。如果当前线程一直存在而没有调用ThreadLocal的remove方法，并且这时候其它地方还是有对ThreadLocal的引用，则当前线程的ThreadLocalMap变量里面会存在ThreadLocal变量的引用和value对象的引用是不会被释放的，这就会造成内存泄露的。但是考虑如果这个ThreadLocal变量没有了其他强依赖，而当前线程还存在的情况下，由于线程的ThreadLocalMap里面的key是弱依赖，则当前线程的ThreadLocalMap里面的ThreadLocal变量的弱引用会被在gc的时候回收，但是对应value还是会造成内存泄露，这时候ThreadLocalMap里面就会存在key为null但是value不为null的entry项。其实在ThreadLocal的set和get和remove方法里面有一些时机是会对这些key为null的entry进行清理的，但是这些清理不是必须发生的，下面简单说下ThreadLocalMap的remove方法的清理过程
     * 总结：ThreadLocalMap内部Entry中key使用的是对ThreadLocal对象的弱引用，这为避免内存泄露是一个进步，因为如果是强引用，那么即使其他地方没有对ThreadLocal对象的引用，ThreadLocalMap中的ThreadLocal对象还是不会被回收，而如果是弱引用则这时候ThreadLocal引用是会被回收掉的，虽然对于的value还是不能被回收，这时候ThreadLocalMap里面就会存在key为null但是value不为null的entry项，虽然ThreadLocalMap提供了set,get,remove方法在一些时机下会对这些Entry项进行清理，但是这是不及时的，也不是每次都会执行的，所以一些情况下还是会发生内存泄露，所以在使用完毕后即使调用remove方法才是解决内存泄露的王道。
     *
     */
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
