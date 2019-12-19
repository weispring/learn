package com.lxc.learn.designpattern.state;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2019/12/19
 */
@Slf4j
public class ConcreteStateA extends State{

    @Override
    public void Handle(ContextFlow context) {
        log.info("{} 审核 ： {}",this.getClass().getSimpleName(), context.getRequest());
        if (context.getReviewStatus() == 11){
            log.info("申请：{}，上级批复：{}",context.getRequest(),context.getMessage());
            setUserName("Level A : 张三");
            context.setMessage("同意");
            context.setReviewStatus(22);
            context.setState(new ConcreteStateB());
        }else {
            log.info("{} 驳回了申请 ： {}",this.getClass().getSimpleName(), context.getRequest());
            context.setState(new ConcreteStateE());
        }
         //设置A的下一个状态是B
        if (context.getState() != null){
            context.getState().Handle(context);
        }
    }

}
