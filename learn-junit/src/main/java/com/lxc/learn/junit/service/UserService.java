package com.lxc.learn.junit.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
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


    @ApiLog
    public User add(User user){
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.insert(user);
        list();
        return user;
    }

    @ApiLog
    public List<User> list(){
        //封装条件
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.where("1=1");
  /*      ew.and("deleted={0}", 1);*/
        return userMapper.selectList(ew);
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
        System.out.println(ApiLog.class.getTypeName());
    }
}
