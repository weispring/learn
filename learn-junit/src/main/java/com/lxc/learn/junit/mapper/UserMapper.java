package com.lxc.learn.junit.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lxc.learn.junit.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}