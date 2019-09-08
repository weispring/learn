package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.service.FactoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/streamClose")
    public Resp upload(@RequestBody User user, HttpServletResponse response) throws Exception{
        response.getOutputStream().write("测试streamClosestreamClosestreamClose".getBytes());
        //需要刷新缓冲
        response.getOutputStream().flush();
        //若不关闭，返回值也会写道输入流中
        response.getOutputStream().close();
        //关闭后不会再写，没有报错？
        response.getOutputStream().write("测试streamClosestreamClosestreamClose".getBytes());
        return RespUtil.convertResult(true);
    }



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


    @RequestMapping(value = "/testGet",method = RequestMethod.GET)
    public Resp upload(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Connection", "keep-alive");
        return RespUtil.convertResult(true);

    }


    @RequestMapping(value = "/testPost",method = RequestMethod.POST)
    public Resp upload1(HttpServletRequest request){
        return RespUtil.convertResult(true);
    }
}
