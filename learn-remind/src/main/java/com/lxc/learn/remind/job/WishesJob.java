package com.lxc.learn.remind.job;

import com.lxc.learn.remind.constant.EmailTemplateEnum;
import com.lxc.learn.remind.constant.RemindConstant;
import com.lxc.learn.remind.enums.EmailSystemConfigEnum;
import com.lxc.learn.remind.model.entity.RemindEventToMe;
import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.service.EventTomeService;
import com.lxc.learn.remind.service.RemindUserService;
import com.lxc.learn.remind.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 14:00
 * @Description:
 */
@Component
@Slf4j
public class WishesJob {

    @Autowired
    private RemindUserService remindUserService;


    //@Scheduled(cron = "0 0 6,7,8 * * ?")
    //@Scheduled(cron = "0/10 * * * * ?")
    public void wishes(){
        log.info("wishes job 开始执行");
        List<RemindUser> list = remindUserService.list();
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(e->{
            List<String> fills = new ArrayList<>();
            fills.add(e.getfNickName());
            SendMailUtil.sendEmail(EmailSystemConfigEnum.COMMON_ACCOUNT,e.getfQqNumber()+RemindConstant.QQ_EMAIL, EmailTemplateEnum.FRIEND_BIRTH.getCode(),fills.toArray());
        });
    }
}
