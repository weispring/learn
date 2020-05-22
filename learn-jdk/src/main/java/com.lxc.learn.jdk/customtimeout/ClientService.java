package com.lxc.learn.jdk.customtimeout;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 20:38
 * @Description:
 */
@Slf4j
public class ClientService {

    public static void main(String[] args) throws InterruptedException {
   /*     int timeout = 3;
        ThirdResp thirdResp = new ThirdResp();
        new ThirdService(thirdResp).start();
        Thread.sleep(timeout * 1000);
        if (thirdResp.getCode() == null){
            log.error("调用超时：{}");
        }*/


        TimeoutThread t = new TimeoutThread(3000,new TimeoutThread.TimeoutException("超时"),Thread.currentThread());

        try{
            t.start();
            //要检测超时的程序段
            boolean flag = true;
            while (flag){

            }

            t.cancel();
        }catch (TimeoutThread.TimeoutException e) {
            System.out.print("--------");
            log.error(e.getMessage(),e);
        }
        System.out.print("000000000");

    }

}
