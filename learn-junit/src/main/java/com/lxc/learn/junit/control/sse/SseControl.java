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
     * 问题：
     * 1.sse是否是全部输出完，客户端才能获取结果？测试时，貌似是的。
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
    public void ssePrefect(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Random r = new Random();
        Long start = System.currentTimeMillis();
        String resp = "As shown in the figure that follows, the query example was first executed in the Visual SQL editor. Next, an Explain report was generated by clicking Query and then Explain Current Statement from the menu. The initial report shows a Visual Explain image with information that appears when you move your pointer device over the orders table in full table scan.";
        while (true){
            byte[] bytes = ("data:"+System.currentTimeMillis()+resp+"\n\n").getBytes();
            try {
                response.getOutputStream().write(bytes);
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() - start > 6000){
                break;
            }
        }
        //response.getOutputStream().flush();
      /*  String resp = "data:Testing 1,2,3" + r.nextInt() +"\n\n";
        response.getOutputStream().write(resp.getBytes());*/
    }

}