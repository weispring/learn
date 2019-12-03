package com.lxc.learn.junit.control.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
/**
 * @author lixianchun
 * @Description
 * @date 2019/12/3 20:36
 */
@Slf4j
@RestController
public class ServletAsync {

    private static final long serialVersionUID = 1L;

    private final static int DEFAULT_TIME_OUT = 10 * 60 * 1000;


    @RequestMapping(value = "doget")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);//注意这里

        AsyncContext actx = req.startAsync(req, resp);
        actx.setTimeout(DEFAULT_TIME_OUT);
        actx.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event complete:" + arg0.getSuppliedRequest().getRemoteAddr());
            }

            @Override
            public void onError(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event has error");
            }

            @Override
            public void onStartAsync(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event start:" + arg0.getSuppliedRequest().getRemoteAddr());
            }

            @Override
            public void onTimeout(AsyncEvent arg0) throws IOException {
                // TODO Auto-generated method stub
                System.out.println("[echo]event time lost");
            }
        });
        new Thread(new AsyncWebService(actx)).start();
    }
}

class AsyncWebService implements Runnable {
    AsyncContext ctx;

    public AsyncWebService(AsyncContext ctx) {
        this.ctx = ctx;
    }

    public void run() {
        for (int i=0;i<5;i++){
            try {
                //等待十秒钟，以模拟业务方法的执行
                Thread.sleep(1000);
                PrintWriter out = ctx.getResponse().getWriter();
                out.println("data:中文" + new Date() + "\r\n");  //js页面EventSource接收数据格式：data：数据 + "\r\n"

                //out.flush();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ctx.complete();
    }

}
