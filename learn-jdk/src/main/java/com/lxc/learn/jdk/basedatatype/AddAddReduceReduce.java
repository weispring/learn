package com.lxc.learn.jdk.basedatatype;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author lixianchun
 * @Description
 * @date 2019/9/11 21:29
 */
@Slf4j
public class AddAddReduceReduce {

    /**
     * a++ 或者 ++a ，都会使a本身加1，但是
     * 当还有其他操作符时，a++ ,加操作最后进行；++a,则是加操作首先执行。
     * @param args
     */
    public static void main(String[] args) {
        int a = 1;
        log.info("a:{}", a);
        if (a++>1){
            log.info("a: true");
        }else {
            log.info("a: fa1se");//
        }
        //a = a ++;
        a++;
        log.info("a:{}", a);//3
        //a = a--;
        a--;
        log.info("a:{}", a);//2
    }


    @Test
    public void test(){
        int a = 1;
        log.info("{}",++a);
        a = ++a;
       log.info("{}",a);
    }


    /**
     * ERROR
     */
    @Test
    public void test001(){
        int i=1;
        i = i++;
        i = i++;
        log.info("{}",i);
        i = ++ i;
        log.info("{}",i);
    }

}
