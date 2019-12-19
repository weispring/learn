package com.lxc.learn.designpattern.adapter;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.OBJ_ADAPTER;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ThirdParty {

    /**
     * 待适配接口
     */
    public void disoal(Object requst){
        log.info("第三方接口处理：{}",requst);
    }

}
