package com.lxc.learn.remind.controller;

import com.lxc.learn.remind.job.RemindMeJob;
import com.lxc.learn.remind.job.WishesJob;
import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.service.RemindUserService;
import com.lxc.learn.remind.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private RemindMeJob remindMeJob;
    @Autowired
    private WishesJob wishesJob;


    @RequestMapping("/test")
    public ReturnCode test(HttpServletRequest request){
        String type = request.getParameter("type");
        if ("1".equals(type)){
            remindMeJob.remindMe();
        }else if ("2".equals(type)){
            wishesJob.wishes();
        }

        return ReturnCode.SUCCESS;
    }

}
