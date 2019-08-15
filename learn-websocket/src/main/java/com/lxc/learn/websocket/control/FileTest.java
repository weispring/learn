package com.lxc.learn.websocket.control;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/13 15:40
 */
@Slf4j
@Controller
public class FileTest {


    @RequestMapping("/png")
    public void webSocket(HttpServletRequest request, HttpServletResponse response){
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("java.pdf");

        response.setHeader("Content-Type", "image/png");
        try {
            byte[] bytes = new byte[1024];
            int n;
            while ((n=is.read(bytes)) != -1){
                response.getOutputStream().write(bytes,0,n);
            }
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    public static void main(String[] args) throws Exception{
        Date date = new Date(631900800000L);


        Integer a = 1;
        Integer b = null;
        if (null == a && b == null?true:b<5){

            System.out.println("");

        }
     /*   Long start = System.currentTimeMillis();
        File destinationDir = new File("./11.png");
        if (!destinationDir.exists()){
            destinationDir.createNewFile();
        }

        Thumbnails.of("./")
                .size(600, 300)
                .toFile(destinationDir);*/

        log.info("{}", System.currentTimeMillis() - 1);
    }
}
