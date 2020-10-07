package com.lxc.learn.vue.controler;

import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.vue.model.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Auther: lixianchun
 * @Date: 2020/10/6 09:54
 * @Description:
 */
@RestController
@RequestMapping("/vue")
@Slf4j
public class VueControler {

    /**
     * 采用 @CrossOrigin或者CorsConfiguration 都可以解决跨域问题，作用范围不同而已
     * options 请求携带的跨域header响应时需要回应
     *手动设置Header有缺陷，因为options 时，请求应该是在过滤器里面就被return了，无法进入controller设置header

     */
    //@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "Content-Type",methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @RequestMapping(value = "test")
    public Resp data(@RequestBody Data d, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","Content-Type");
        Data data = new Data();
        data.setAnswer("哈哈" + UUID.randomUUID().toString());
        return RespUtil.success(data);
    }

}
