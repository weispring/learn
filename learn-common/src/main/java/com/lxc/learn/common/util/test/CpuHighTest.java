package com.lxc.learn.common.util.test;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * cpu消耗
 *常见服务器CPU过高的问题原因和对应解决方案：
 1、方法中存在读写文件流的操作，高并发时每个请求产生一个文件流，导致系统CPU急增。
 解决方案：
 从线程栈日志信息中，找出导致CPU高的线程方法。读写文件流操作移出方法中，避免每次请求都产生一个文件流。

 2、方法中使用了多线程，未使用连接池或使用了Executors.newCachedThreadPool()创建的接连池，高并发时创建出过多的后台线程。
 解决方案：
 a、使用jstack命令统计出线程数量；
 b、找出程序中创建线程代码；
 c、使用Executors.newFixedThreadPool(thread_size)创建固定数量的线程池（线程数固定，无法无收），或者使用new ThreadPoolExecutor(coreSize, maxSize, 60L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>())直接构造线程池。

 3、发现是gc线程导致的CPU问题比较费时
 解决方案：
 a、查看一下gc策略是否合理；
 b、用命令jmap -histo [PID] 分析是哪个类占用内存比较多，分析出可能存在内存泄露的地方；
 c、jvm内存调优，可使用Jconsole、visalvm、probe等工具查看java虚拟机中方法区、堆区（新生代、幸存代、老年代）、线程栈的内存分配，根据实际情况进行优化。

 4、代码中存在死循环、死锁
 解决方案：
 直接通过jstack获取到出问题的线程的堆栈信息，分析代码逻辑。


 * @author lixianchun
 * @Description
 * @date 2019/7/17 14:58
 */
@Slf4j
public class CpuHighTest {
    public static void main(String[] args) throws Exception {
       /* while (true){
            log.info("循环:{}",Math.pow(2, 1));
        }*/

       for (int i=0;i<10000;i++){
           new Thread().start();
           log.info("{}",i);
       }
     /* for (int i=0;i<1000;i++){
          File file = new File("./"+i+".txt");
          file.createNewFile();
          FileInputStream fis = new FileInputStream(file);
      }*/
    }
}
