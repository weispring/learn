package com.lxc.learn.remind.model.dao;

import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.model.entity.RemindUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RemindUserMapper {
    long countByExample(RemindUserExample example);

    int deleteByExample(RemindUserExample example);

    int deleteByPrimaryKey(Long fId);

    int insert(RemindUser record);

    int insertSelective(RemindUser record);

    List<RemindUser> selectByExample(RemindUserExample example);

    RemindUser selectByPrimaryKey(Long fId);

    int updateByExampleSelective(@Param("record") RemindUser record, @Param("example") RemindUserExample example);

    int updateByExample(@Param("record") RemindUser record, @Param("example") RemindUserExample example);

    int updateByPrimaryKeySelective(RemindUser record);

    int updateByPrimaryKey(RemindUser record);
}