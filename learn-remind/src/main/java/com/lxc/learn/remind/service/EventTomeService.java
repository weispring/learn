package com.lxc.learn.remind.service;

import com.lxc.learn.remind.model.dao.RemindEventToMeMapper;
import com.lxc.learn.remind.model.entity.RemindEventToMe;
import com.lxc.learn.remind.model.entity.RemindEventToMeExample;
import com.lxc.learn.remind.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 12:08
 * @Description:
 */
@Service
public class EventTomeService {

    @Autowired
    private RemindEventToMeMapper remindEventToMeMapper;

    public List<RemindEventToMe> listEvents(){
        RemindEventToMeExample example = new RemindEventToMeExample();
        example.createCriteria().andFRemindDateEqualTo(DateUtil.getYmd());
        return remindEventToMeMapper.selectByExample(example);
    }

    public void setNextTime(Long id,String time){
        RemindEventToMe remindEventToMe = new RemindEventToMe();
        remindEventToMe.setfId(id);
        remindEventToMe.setfRemindDate(time);
        remindEventToMe.setfSysUpdatedTime(new Date());
        remindEventToMeMapper.updateByPrimaryKeySelective(remindEventToMe);
    }
}
