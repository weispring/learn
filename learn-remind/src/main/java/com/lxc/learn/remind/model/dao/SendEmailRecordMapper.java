package com.lxc.learn.remind.model.dao;

import com.lxc.learn.remind.model.entity.SendEmailRecord;
import com.lxc.learn.remind.model.entity.SendEmailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SendEmailRecordMapper {
    long countByExample(SendEmailRecordExample example);

    int deleteByExample(SendEmailRecordExample example);

    int deleteByPrimaryKey(Long fId);

    int insert(SendEmailRecord record);

    int insertSelective(SendEmailRecord record);

    List<SendEmailRecord> selectByExampleWithBLOBs(SendEmailRecordExample example);

    List<SendEmailRecord> selectByExample(SendEmailRecordExample example);

    SendEmailRecord selectByPrimaryKey(Long fId);

    int updateByExampleSelective(@Param("record") SendEmailRecord record, @Param("example") SendEmailRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") SendEmailRecord record, @Param("example") SendEmailRecordExample example);

    int updateByExample(@Param("record") SendEmailRecord record, @Param("example") SendEmailRecordExample example);

    int updateByPrimaryKeySelective(SendEmailRecord record);

    int updateByPrimaryKeyWithBLOBs(SendEmailRecord record);

    int updateByPrimaryKey(SendEmailRecord record);
}