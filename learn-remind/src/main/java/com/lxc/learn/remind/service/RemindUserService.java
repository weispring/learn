package com.lxc.learn.remind.service;

import com.lxc.learn.remind.model.dao.RemindUserMapper;
import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.model.entity.RemindUserExample;
import com.lxc.learn.remind.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemindUserService {

    @Autowired
    private RemindUserMapper remindUserMapper;

    public List<RemindUser> list() {
        RemindUserExample example = new RemindUserExample();
        example.createCriteria().andFBirthDayEqualTo(DateUtil.birthDay());
        return remindUserMapper.selectByExample(example);
    }
}
