package com.lxc.learn.remind.controller;

import com.lxc.learn.remind.model.entity.RemindUser;
import com.lxc.learn.remind.service.RemindUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RemindUserController {

    @Autowired
    private RemindUserService remindUserService;

    @RequestMapping("/listUser")
    public List<RemindUser> listUser(HttpServletRequest request){
        return remindUserService.list();
    }

}
