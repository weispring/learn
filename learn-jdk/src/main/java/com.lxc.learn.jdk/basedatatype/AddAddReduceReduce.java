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
        a = ++a;
       log.info("{}",a);
    }


}
