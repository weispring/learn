package com.lxc.learn.designpattern.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ConcreteStateB extends State{

    @Override
    public void Handle(ContextFlow context) {
        log.info("{} 审核 ： {}",this.getClass().getSimpleName(), context.getRequest());
        if (context.getReviewStatus() == 22){
            log.info("申请：{}，上级批复：{}",context.getRequest(),context.getMessage());
            setUserName("Level B : 李四");
            context.setMessage("同意");
            context.setReviewStatus(33);
            context.setState(new ConcreteStateC());
        }else {
            log.info("流程结束");
        }
        //设置A的下一个状态是C
        if (context.getState() != null){
            context.getState().Handle(context);
        }
    }
}

