package com.lxc.learn.junit.control.cors;

import com.lxc.learn.junit.aop.ApiLog;
import com.lxc.learn.junit.aop.Second;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lixianchun
 * @Date: 2020/2/14 19:38
 * @Description:
 */
@RequestMapping(value = "/cors")
@Controller
public class CorsConrol1 {

    @RequestMapping(method = RequestMethod.GET, value = "/cors1")
    String cors1(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-origin","http://localhost:9999");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        return "cors2";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/cors2")
    String cors2(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-origin","http://localhost:9990");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        return "cors2";
    }


}
