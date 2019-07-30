package com.lxc.learn.websocket.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 入口，类似于进群
 * @author lixianchun
 * @Description
 * @date 2019/7/29 19:51
 */
@Slf4j
@Controller
public class WsController {

    @RequestMapping("/websocket/{name}")
    public ModelAndView webSocket(@PathVariable String name, Model model){
        ModelAndView modelAndView = new ModelAndView();
        try{
            log.info("跳转到websocket的页面上");
            modelAndView.addObject("username",name);
            modelAndView.setViewName("ws");
            return modelAndView;
        }
        catch (Exception e){
            log.info("跳转到websocket的页面上发生异常，异常信息是："+e.getMessage());
            modelAndView.setViewName("ws");
            return modelAndView;
        }
    }
}
