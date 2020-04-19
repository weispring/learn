package com.lxc.learn.junit.mapper;

import com.lxc.learn.junit.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author li xian chun
 * @since 2020-04-17
 */
public interface UserMapper extends BaseMapper<User> {

    User getUserDetail(@Param("id") Long id);


    User dynamic(User user);
}
