package com.lxc.learn.remind.service;

import com.lxc.learn.remind.model.dao.SendEmailRecordMapper;
import com.lxc.learn.remind.model.entity.RemindEmailTemplate;
import com.lxc.learn.remind.model.entity.RemindEmailTemplateExample;
import com.lxc.learn.remind.model.entity.SendEmailRecord;
import com.lxc.learn.remind.model.entity.SendEmailRecordExample;
import com.lxc.learn.remind.util.BusinessException;
import com.lxc.learn.remind.util.DateUtil;
import com.lxc.learn.remind.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 15:08
 * @Description:
 */
@Service
public class SendEmailRecordService {
    @Autowired
    private SendEmailRecordMapper sendEmailRecordMapper;

    public void saveRecord(String from, String to, String templateCode){
        SendEmailRecord sendEmailRecord = new SendEmailRecord();
        sendEmailRecord.setfEmailFrom(from);
        sendEmailRecord.setfEmailTo(to);
        sendEmailRecord.setfEmailTemplateCode(templateCode);
        sendEmailRecordMapper.insertSelective(sendEmailRecord);
    }

    public boolean exist(String from, String to, String templateCode){
        SendEmailRecordExample example = new SendEmailRecordExample();
        example.createCriteria().andFEmailTemplateCodeEqualTo(templateCode)
                .andFEmailFromEqualTo(from)
                .andFEmailToEqualTo(to)
                .andFSysCreatedTimeBetween(DateUtil.getDayBegin(),DateUtil.getDayEnd());
        List<SendEmailRecord> list = sendEmailRecordMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)){
            return false;
        }
        return true;
    }


}
