package com.lxc.learn.remind.model.dao;

import com.lxc.learn.remind.model.entity.RemindEmailTemplate;
import com.lxc.learn.remind.model.entity.RemindEmailTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RemindEmailTemplateMapper {
    long countByExample(RemindEmailTemplateExample example);

    int deleteByExample(RemindEmailTemplateExample example);

    int deleteByPrimaryKey(Long fId);

    int insert(RemindEmailTemplate record);

    int insertSelective(RemindEmailTemplate record);

    List<RemindEmailTemplate> selectByExampleWithBLOBs(RemindEmailTemplateExample example);

    List<RemindEmailTemplate> selectByExample(RemindEmailTemplateExample example);

    RemindEmailTemplate selectByPrimaryKey(Long fId);

    int updateByExampleSelective(@Param("record") RemindEmailTemplate record, @Param("example") RemindEmailTemplateExample example);

    int updateByExampleWithBLOBs(@Param("record") RemindEmailTemplate record, @Param("example") RemindEmailTemplateExample example);

    int updateByExample(@Param("record") RemindEmailTemplate record, @Param("example") RemindEmailTemplateExample example);

    int updateByPrimaryKeySelective(RemindEmailTemplate record);

    int updateByPrimaryKeyWithBLOBs(RemindEmailTemplate record);

    int updateByPrimaryKey(RemindEmailTemplate record);
}