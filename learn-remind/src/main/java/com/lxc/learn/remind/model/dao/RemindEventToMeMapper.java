package com.lxc.learn.remind.model.dao;

import com.lxc.learn.remind.model.entity.RemindEventToMe;
import com.lxc.learn.remind.model.entity.RemindEventToMeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RemindEventToMeMapper {
    long countByExample(RemindEventToMeExample example);

    int deleteByExample(RemindEventToMeExample example);

    int deleteByPrimaryKey(Long fId);

    int insert(RemindEventToMe record);

    int insertSelective(RemindEventToMe record);

    List<RemindEventToMe> selectByExample(RemindEventToMeExample example);

    RemindEventToMe selectByPrimaryKey(Long fId);

    int updateByExampleSelective(@Param("record") RemindEventToMe record, @Param("example") RemindEventToMeExample example);

    int updateByExample(@Param("record") RemindEventToMe record, @Param("example") RemindEventToMeExample example);

    int updateByPrimaryKeySelective(RemindEventToMe record);

    int updateByPrimaryKey(RemindEventToMe record);
}