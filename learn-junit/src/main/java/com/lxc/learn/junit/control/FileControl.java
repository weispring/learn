package com.lxc.learn.junit.control;

import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Req;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.junit.entity.User;
import com.lxc.learn.junit.req.FileDownReq;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/1 11:32
 */
@RequestMapping(value = "/file")
@RestController
@Slf4j
public class FileControl {

    @RequestMapping(value = "/upload")
    public Resp upload(@RequestPart MultipartFile file,String param, HttpServletRequest request) throws Exception{
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String value = enumeration.nextElement();
            log.info("key:{},value:{}",  value, request.getParameter(value));
        }
        Collection<Part> parts = request.getParts();

        String test01 = request.getHeader("test01");
        String test02 = request.getParameter("test02");
        String path = "./";
        String filePath = path + File.separator + file.getOriginalFilename();
        File serverFile = new File(filePath);
        FileOutputStream fos = new FileOutputStream(serverFile);
        InputStream is = file.getInputStream();
        byte[] cache = new byte[1024];
        int n;
        while ((n=is.read(cache)) != -1){
            fos.write(cache,0,n);
        }
        fos.flush();
        return RespUtil.convertResult(true);
    }


    @RequestMapping(value = "/down")
    public Resp down(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, String> param = WebUtil.getRequestParams(request);
        String path = "./";
        String fileName = "bossbr上台接口.xlsx";
        String filePath = path + File.separator + fileName;
        File serverFile = new File(filePath);

        InputStream is = new FileInputStream(serverFile);
        byte[] cache = new byte[1024];
        int n;
        while ((n=is.read(cache)) != -1){
            response.getOutputStream().write(cache,0,n);
        }
        response.getOutputStream().flush();
        return null;
    }

}
