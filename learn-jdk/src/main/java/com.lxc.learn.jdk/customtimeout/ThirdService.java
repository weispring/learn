package com.lxc.learn.jdk.customtimeout;

/**
 * @Auther: lixianchun
 * @Date: 2020/5/22 20:32
 * @Description:
 */
public class ThirdService extends Thread{

    private volatile ThirdResp thirdResp;

    /**
     * 构造器
     */
    public ThirdService(ThirdResp thirdResp) {
        super();
        this.thirdResp = thirdResp;
        //设置本线程为守护线程
        this.setDaemon(true);
    }


    /**
     * 执行大概需要5second
     */
    public void run(){
        try {
            Thread.sleep(5*1000);
            thirdResp.setCode("success");
            thirdResp.setMsg("成功");
            thirdResp.setDataInfo("2020");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
