package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.config.ReadConfigFile;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.FactoriesService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:32
 */
@RequestMapping(value = "/test")
@RestController
@Slf4j
public class TestControl {

    @Autowired
    private FactoriesService factoriesService;

    private List list = Arrays.asList("1","2");

/*
    @Value("${test.web}")
    private String testWeb;
*/


    @RequestMapping(value = "/testjson")
    public Resp upload(@RequestBody List<Long> ids){
        if (true){
            throw new RuntimeException("000000000000");
        }


        log.info("测试当前:{}", ids);
        return RespUtil.convertResult(true);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Resp handleException(HttpServletRequest request, HttpServletResponse response, RuntimeException ex) {
        Resp baseResponse = RespUtil.convertResult(true);
        log.error("异常:{},{}", ex.getMessage(),ex);
        return baseResponse;
    }


    @RequestMapping(value = "/testPost",method = RequestMethod.POST)
    public Resp upload1(HttpServletRequest request,HttpServletResponse response){
        response.setHeader("outClassName",this.getClass().getName());
        return RespUtil.convertResult(true);
    }


    @RequestMapping(value = "/testListChange",method = RequestMethod.GET)
    public Resp testListChange(HttpServletRequest request){
        String s = request.getParameter("list");
        String[] arr = s.split(",");
        list = Arrays.asList(arr);
        return RespUtil.convertResult(true);
    }

    @RequestMapping(value = "/testListChange1",method = RequestMethod.GET)
    public Resp testListChange1(HttpServletRequest request){
        log.info("集合list11:{}",list);
        return RespUtil.convertResult(true);
    }


    @RequestMapping(value = "/testPoint",method = RequestMethod.GET)
    public Resp testPoint(HttpServletRequest request){
        Resp resp = RespUtil.success();
        User o = null;
        resp.setBody(o);
        o = new User();
        o.setName("1001");
        User user = o;
        user.setName("10002");
        return RespUtil.convertResult(true);
    }

    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @RequestMapping(value = "/testMysql",method = RequestMethod.GET)
    public Resp testMysql(HttpServletRequest request){
        String sql = "INSERT INTO `learn`.`customer_info_1` (`id`, `cmhk_customer_id`, `company_name`, `last_name`, `cc_code`, `first_name`, `gender`, `birthday`, `certificate_type`, `certificate_code`, `contact_number`, `email`, `address_info`, `created_by`, `created_time`, `updated_by`, `updated_time`, `remark`, `deleted`, `customer_category`, `cn_last_name`, `certificate_address_info`, `main_cust_code`, `main_cust_pwd`, `br_no`, `customer_group`, `referrer_passportno`, `referrer_msisdn`, `sub_cust_id`) VALUES ('BBB', NULL, '微品', '王', '1234', '大睿', '1', '20171102', '2', '440982199812055436', '75643829', '13692211045@163.com', 'AAA', NULL, '1510127377259', NULL, '1510127377259', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);";

        sql = sql.replace("BBB",Integer.valueOf(atomicInteger.incrementAndGet()+10000).toString());
        sql = sql.replace("AAA",request.getParameter("AAA"));
        new JdbcTest().executeSql(sql);
        return RespUtil.convertResult(true);
    }

    @RequestMapping(value = "/testReadFile",method = RequestMethod.GET)
    public Resp testReadFile(HttpServletRequest request){
        log.info("入参path:{}",request.getParameter("path"));
        ReadConfigFile.readConfig(new String[]{request.getParameter("path")});
        return RespUtil.convertResult(true);
    }


    {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("集合list:{}",list);
            }
        }.start();
    }


    @RequestMapping(value = "/testAsync",method = RequestMethod.GET)
    public void test(HttpServletResponse response) throws InterruptedException {
        log.info("测试异步处理");
        Thread.sleep(3000);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(3000);
                response.getOutputStream().write("测试异步处理".getBytes());
            }
        }).start();
    }
}
