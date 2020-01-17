package com.lxc.learn.jvm;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/1/16
 */
@Slf4j
/**
 *VM Args：-Xss128k
 *@author zzm
 */
public class JavaVMStackSOF{
    private int stackLength=1;
    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void main(String[]args)throws Throwable{
        JavaVMStackSOF oom=new JavaVMStackSOF();
        try{
            oom.stackLeak();
        }catch(Throwable e){
            System.out.println("stack length："+oom.stackLength);
            throw e;
        }
    }
}
