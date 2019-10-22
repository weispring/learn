package com.lxc.learn.junit.service;

import com.lxc.learn.junit.aop.BossInterfaceLog;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.entity.UserExample;
import com.lxc.learn.junit.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.InstantiationModelAwarePointcutAdvisor;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.target.SingletonTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/8 20:36
 * @Description:
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User add(User user){
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insertSelective(user);
        return user;
    }

    @BossInterfaceLog(value = "22")
    public List<User> list(){
        UserExample userExample = new UserExample();
        userExample.createCriteria();
        return userMapper.selectByExample(userExample);
    }

/*    public static void main(String[] args) {

        Object service = null;

        AdvisedSupport advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new SingletonTargetSource(service);
        advisedSupport.setTargetSource(targetSource);
        InstantiationModelAwarePointcutAdvisorImpl advisor = new InstantiationModelAwarePointcutAdvisorImpl();
        advisedSupport.addAdvice(advisor);

    }*/

    public static void main(String[] args) {
        System.out.println(BossInterfaceLog.class.getTypeName());
    }
}
