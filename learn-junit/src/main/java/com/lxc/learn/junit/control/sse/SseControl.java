package com.lxc.learn.junit.control.sse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * @Auther: lixianchun
 * @Date: 2019/12/2 22:30
 * @Description:
 */
@RestController
public class SseControl {

    /**
     * 1.若是control return ,则tomcat容器会关闭http连接
     * 2.sse推送的报文格式
     * 3.正确的响应客户端，通过输出流
     * @param request
     * @return
     */

    @RequestMapping(value = "push",produces = {"text/event-stream"})
    public Object sse(HttpServletRequest request){
        Random r = new Random();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "data:Testing 1,2,3" + r.nextInt() +"\n\n";
    }

    @RequestMapping(value = "pushPrefect",produces = {"text/event-stream"})
    public Object ssePrefect(HttpServletRequest request, HttpServletResponse response){
        Random r = new Random();
        for (int i=0;i<10;i++){
            byte[] bytes = ("data:"+new Date().toString()+"\r\n").getBytes();
            try {
                response.getOutputStream().write(bytes);
                response.getOutputStream().flush();
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "data:Testing 1,2,3" + r.nextInt() +"\n\n";
    }

}
