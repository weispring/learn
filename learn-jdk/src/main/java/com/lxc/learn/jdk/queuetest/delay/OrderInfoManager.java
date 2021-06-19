package com.lxc.learn.jdk.queuetest.delay;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/20 9:03
 */
@Slf4j
public class OrderInfoManager {

    /**
     1.简介
     DelayQueue是一个无界阻塞队列，只有在延迟期满时，才能从中提取元素。
     队列的头部，是延迟期满后保存时间最长的delay元素。

     2.使用场景：
     缓存系统设计：使用DelayQueue保存缓存元素的有效期，用一个线程循环查询DelayQueue，一旦从DelayQueue中取出元素，就表示有元素到期。
     定时任务调度：使用DelayQueue保存当天要执行的任务和执行的时间，一旦从DelayQueue中获取到任务，就开始执行，比如Timer，就是基于DelayQueue实现的。

     3.使用条件：
     存放DelayQueue的元素，必须继承Delay接口，Delay接口使对象成为延迟对象。
     该接口强制实现两个方法：
     1.CompareTo(Delayed o)：用于比较延时，队列里元素的排序依据，这个是Comparable接口的方法，因为Delay实现了Comparable接口，所以需要实现。
     2.getDelay(TimeUnit unit)：这个接口返回到激活日期的--剩余时间，时间单位由单位参数指定。
     此队列不允许使用null元素。

     延迟队列 依赖于 优先级队列

     */
    public static DelayQueue<OrderInfoDelayBean> DELAY_QUEUE = new DelayQueue<>();

    static Random random = new Random(10);

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            OrderInfoDelayBean orderInfoDelayBean = new OrderInfoDelayBean();
            int ran = random.nextInt(10);
            int num = (1 + ran)*1000;
            orderInfoDelayBean.setDueTime(System.currentTimeMillis() + num);
            orderInfoDelayBean.setSerialNumber(num + "");
            addOrderInfoDelay(orderInfoDelayBean);
        }

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                try {
                    OrderInfoDelayBean orderInfoDelayBean = null;
                    while((orderInfoDelayBean=DELAY_QUEUE.take())!=null) {
                        log.info("=-=-=-=-=-=-=-=-=-=-= 清除下派给散铺的过期订单【"+orderInfoDelayBean.getSerialNumber()+"】成功,当前时间"+new Date().getTime());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }

    public static void addOrderInfoDelay(OrderInfoDelayBean orderInfoDelayBean){
        if(!DELAY_QUEUE.contains(orderInfoDelayBean)){
            DELAY_QUEUE.add(orderInfoDelayBean);
            log.info("订单"+orderInfoDelayBean.getSerialNumber()+"进入延时队列，当前时间"+new Date().getTime());
        }
    }


}
