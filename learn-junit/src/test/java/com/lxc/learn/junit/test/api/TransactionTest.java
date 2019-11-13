package com.lxc.learn.junit.test.api;

import com.lxc.learn.junit.transaction.ServiceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/13 15:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class TransactionTest  {

    @Autowired
    private ServiceTest serviceTest;

    @Test
    public void test(){
        serviceTest.savetest();
    }

    @Test
    public void saveRequiredAndRequiredNew(){
       serviceTest.saveRequiredAndRequiredNew();
    }


    /**
     * 本身sql 异常或者保存后的其他exception 会影响外部事物回滚
     * 适用于事物内部的日志保存，不受事物本身影响
     */
    @Test
    public void saveRequiredAndNotSupportted(){
        serviceTest.saveRequiredAndNotSupportted();
    }

    @Test
    public void saveRequiredAndNested(){
        serviceTest.saveRequiredAndNested();
    }

}
