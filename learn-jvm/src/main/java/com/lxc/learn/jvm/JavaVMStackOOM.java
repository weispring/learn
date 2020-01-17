package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/16
 */
@Slf4j
/**
 *VM Args：-Xss2M(这时候不妨设置大些)
 *@author zzm
 */
public class JavaVMStackOOM{

    private void dontStop(){
        while(true){
        }
    }


        public void stackLeakByThread(){
            while(true){
                Thread thread=new Thread(new Runnable(){
                    @Override
                    public void run(){
                        dontStop();
                    }
                });
                thread.start();
            }
        }

        public static void main(String[]args)throws Throwable{
            JavaVMStackOOM oom=new JavaVMStackOOM();
            oom.stackLeakByThread();
        }
}
