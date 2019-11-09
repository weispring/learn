package com.lxc.learn.designpattern.log;

import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.designpattern.order.req.OrderReqBody;
import com.lxc.learn.designpattern.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/8 15:14
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestLogControl {

    @RequestMapping("/oom")
    public Resp oom(@RequestBody Req<OrderReqBody> req){
        //1 G
        byte[] bytes = new byte[1024*1024*1024];
        return RespUtil.success();
    }
}
