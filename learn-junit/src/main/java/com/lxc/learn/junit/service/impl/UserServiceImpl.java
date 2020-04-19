package com.lxc.learn.junit.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.mapper.UserMapper;
import com.lxc.learn.junit.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    //@ApiLog
    public User add(User user){
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        this.baseMapper.insert(user);
        list();
        return user;
    }

    @ApiLog
    public List<User> list(){
        //封装条件
        EntityWrapper<User> ew = new EntityWrapper<User>();
        ew.where("1=1");
        /*      ew.and("deleted={0}", 1);*/
        return this.baseMapper.selectList(ew);
    }

    public User getUserDetail(Long id){
        return this.baseMapper.getUserDetail(id);
    }

}
