package com.lxc.learn.junit.task;

import com.lxc.learn.junit.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/*
*
* spring Scheduled 线程池配置
 * @author lixianchun
 * @Description
 * @date 2019/6/19 18:06
*/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    /**
     * spring task定时器schedule任务阻塞的解决办法
     任务阻塞分两种：一种是一个定时任务阻塞导致全部定时任务阻塞，
     第二种是定时任务方法本身阻塞,影响下一次的执行

     * 第一种情况的解决办法：配置线程池的大小
     * 第二种情况的解决办法：当前定时器调用一个异步的方法去执行所要做的操作

     * 总结：配置线程池大小不能解决方法本身阻塞的问题，只能解决不同定时器之间相互阻塞的问题

     */

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(setTaskExecutors());
    }

    @Bean(destroyMethod="shutdown")
    public Executor setTaskExecutors(){
        // 多个 @Scheduled 可以做到并发执行
        return Executors.newScheduledThreadPool(4);
    }// 3个线程来处理。

}




