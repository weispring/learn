package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpRequestFutureTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/21 10:31
 */
@RestController
@Slf4j
public class Async2syncControl {


    /**
     * 异步转同步
     * 1. concurrentHashMap 保证多线程的时候，能够指定线程唤醒，如果是多节点，需要路由到指定节点，需要指明节点
     * 2. while(true) prepay2 阻塞查询
     * 3. CountDownLatch
     */
    private ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(1024);




    @GetMapping("/prepay")
    public  Resp prepay(HttpServletRequest request) throws Exception{
        log.info("进入支付方法");
        //String orderId = request.getParameter("orderId");
        //Thread.currentThread().setName(orderId);
        //加锁，不然可能还是会 重入，会存在锁覆盖
        //解决方法：用线程id
        //多节点 可加分布式锁
        String orderId = request.getParameter("orderId");
        Thread.currentThread().setName(orderId);

        //String orderId = request.getParameter("orderId");
        //Thread.currentThread().setName(orderId);
            // 通过线程id优化异步转同步
        //加锁，不然可能还是会 重入，会存在锁覆盖
        //解决方法：用线程id
        //多节点 可加分布式锁
        Long threadId = Thread.currentThread().getId();
        concurrentHashMap.put(threadId, new Object());

        log.info("当前线程：{},{}，等待",Thread.currentThread().getName(),Thread.currentThread().getId());
        //设置超时时间（包括超时移除），以及加上异步结果，以便判断是否异步正确响应了
        concurrentHashMap.get(threadId).wait(30*1000);

        log.info("线程执行完毕：{},{}，等待",Thread.currentThread().getName(),Thread.currentThread().getId());
        return RespUtil.success( "sucsess");
    }


    @GetMapping("/nofity")
    public Resp nofity(HttpServletRequest request){
        String threadId = request.getParameter("threadId");
        log.info("当前线程：{},{}，唤醒",Thread.currentThread().getName(),Thread.currentThread().getId());

        //可能超时已经移除
        concurrentHashMap.get(threadId).notify();
        return RespUtil.success( "nofity");
    }



    @GetMapping("/prepay2")
    public Resp prepay2(HttpServletRequest request){
        //逻辑处理

        //阻塞查询
        Long begin = System.currentTimeMillis();
        Long limit = 30*1000L;
        int falg = 0;// 0 失败 1000 成功 5000 异常
        while (true){
            falg = 1000;//获取数据库结果值 1000,0
            if (falg == 1000){
                break;
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - begin < limit){
                falg = 5000;
                break;
            }
        }
        if (falg == 1000){
            //成功
            return RespUtil.success( falg);
        }else {
            //失败
            return RespUtil.fail( String.valueOf(falg));
        }
    }


}
