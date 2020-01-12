package com.lxc.learn.remind.job;

import com.lxc.learn.remind.constant.RemindIntervalType;
import com.lxc.learn.remind.enums.EmailSystemConfigEnum;
import com.lxc.learn.remind.model.entity.RemindEventToMe;
import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.service.EmailTemplateService;
import com.lxc.learn.remind.service.EventTomeService;
import com.lxc.learn.remind.service.RemindUserService;
import com.lxc.learn.remind.util.DateUtil;
import com.lxc.learn.remind.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Auther: lixianchun
 * @Date: 2019/4/16 14:00
 * @Description:
 */
@Component
@Slf4j
public class RemindMeJob {

    @Autowired
    private EventTomeService eventTomeService;


    private final String to = "2629469408@qq.com";

/*    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }*/



    //@Scheduled(cron = "0 0 6,7,8 * * ?")
    @Scheduled(cron = "0/10 * * * * ?")
    public void remindMe(){
        log.info("remindMe 开始执行");
        List<RemindEventToMe> list = eventTomeService.listEvents();
        if (CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(e->{
            try {
                boolean result = SendMailUtil.sendEmail(EmailSystemConfigEnum.ONLY_ME,to,e.getfEmailTemplateCode());
                if (result){
                    eventTomeService.setNextTime(e.getfId(),getNextTime(DateUtil.getNextWeek(DateUtil.format(e.getfRemindDate())),e.getfRemindIntervalType()));
                }
            }catch (Exception ex){
                log.error(ex.getMessage(),e);
            }
        });
    }

    private String getNextTime(Date date,String type){
        Date nextTime = null;
        if (RemindIntervalType.WEEK.getCode().equals(type)){
            nextTime = DateUtil.getNextWeek(date);
        }else if (RemindIntervalType.WEEK.getCode().equals(type)){
            nextTime = DateUtil.getNextMonth(date);
        }else if (RemindIntervalType.WEEK.getCode().equals(type)){
            nextTime = DateUtil.getNextYear(date);
        }else {
            throw new RuntimeException("数据错误");
        }
        return DateUtil.getYmd(date);
    }
}
