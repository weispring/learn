package com.lxc.learn.webservice.control;

import com.lxc.learn.webservice.simple.clien.springcall.SimplerClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: lixianchun
 * @Date: 2019/6/17 23:19
 * @Description:
 */
@RestController
@Slf4j
public class TestControl {

    @Autowired
    private SimplerClient simplerClient;


    @RequestMapping("/simple")
    public Object simple(@RequestParam("name") String name){
        return simplerClient.simple(name);
    }

}
