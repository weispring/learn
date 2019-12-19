package com.lxc.learn.designpattern.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ConcreteStateC extends State{

    @Override
    public void Handle(ContextFlow context) {
        log.info("{} 审核 ： {}",this.getClass().getSimpleName(), context.getRequest());
        if (context.getReviewStatus() == 33){
            log.info("申请：{}，上级批复：{}",context.getRequest(),context.getMessage());
            setUserName("Level B : 王五");
            context.setMessage("同意");
            context.setReviewStatus(44);
        }else {
            log.info("流程结束");
        }
    }
}

