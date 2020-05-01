package com.lxc.learn.junit.control;

import com.lxc.learn.common.constant.SystemConstant;
import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.file.image.PictureUtil;
import com.lxc.learn.jdk.image.Picture;
import com.lxc.learn.junit.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author lixianchun
 * @Description
 * @date 2019/11/1 18:39
 */
@RestController
@Slf4j
public class HttpControl {

    /**
     * 请求已经被实现，而且有一个新的资源已经依据请求的需要而建立，
     * 且其 URI 已经随Location 头信息返回。假如需要的资源无法及时建立的话，应当返回 '202 Accepted'。
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/httpStatus201")
    public Object upload() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode("测试201.txt", SystemConstant.UTF8));
        byte[] bytes = null;
        try {
            bytes = "测试http201".getBytes();
        } catch (Exception e) {
            log.error("read file to byteArray error:{}", e);
        }
        return new ResponseEntity(bytes, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/httpRetryAfter")
    public Object upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("请求入参：{}","");
        //TODO ERROR Retry-After
        //如果某个实体临时不可用，那么此协议头用于告知客户端稍后重试。其值可以是一个特定的时间段(以秒为单位)或一个超文本传输协议日期。
        //response.setHeader("Retry-After",getHttpTime( DateUtil.addTime(new Date(), 5,Calendar.SECOND)));
        return new ResponseEntity(null, null, HttpStatus.SERVICE_UNAVAILABLE);
    }


    @RequestMapping(value = "/httpRefresh")
    public Object httpRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("请求入参：{}","");
        //Refresh 替代Retry-After refresh /应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）
        response.setHeader("Refresh","5;url=http://localhost:9999/httpRefresh");
        return null;
    }

    private static String getHttpTime(Date date){
        // Locale.US用于将日期区域格式设为美国（英国也可以）。缺省改参数的话默认为机器设置，如中文系统星期将显示为汉子“星期六”
        SimpleDateFormat localDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.US);
        SimpleDateFormat greenwichDate = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        // 时区设为格林尼治
        greenwichDate.setTimeZone(TimeZone.getTimeZone("GMT"));

        System.out.println("当前时间：" + localDate.format(date));
        String time = greenwichDate.format(date);
        System.out.println("格林尼治时间：" + time);
        return time;
    }



    @RequestMapping(value = "/test00")
    public Object test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedImage bufferedImage = PictureUtil.createImg(true,true,new String[]{"http://devcloud.vpclub.cn/group1/M00/0A/6B/rBAFJF5gvv6AE4eQAAAsUhBLtyc465.png"
        ,"http://devcloud.vpclub.cn/group1/M00/0A/6B/rBAFJF5gvv6AE4eQAAAsUhBLtyc465.png"
        ,"http://devcloud.vpclub.cn/group1/M00/0A/6B/rBAFJF5gvv6AE4eQAAAsUhBLtyc465.png"
        ,"http://devcloud.vpclub.cn/group1/M00/0A/6B/rBAFJF5gvv6AE4eQAAAsUhBLtyc465.png"
        ,"http://devcloud.vpclub.cn/group1/M00/0A/6B/rBAFJF5gvv6AE4eQAAAsUhBLtyc465.png"});
        PictureUtil.write(bufferedImage,0.8f,response);
        return null;
    }

    @GetMapping(value = "/redirect")
    public Object redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.MOVED_TEMPORARILY.value());
        response.sendRedirect("https://devgw.vpclub.cn/group1/M00/06/49/rBAFJF4JnSGAZpxkAAy8iTYdlGU895.pdf");
        return RespUtil.success();
    }

    @GetMapping(value = "/down")
    public Object down(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("测试pdf下载.pdf", "UTF-8"));
        response.setContentType("application/octet-stream");
        URL url = new URL("https://devgw.vpclub.cn/group1/M00/06/49/rBAFJF4JnSGAZpxkAAy8iTYdlGU895.pdf");
        InputStream in = url.openStream(); //new FileInputStream("");
        int i;
        OutputStream out = response.getOutputStream();
        try{
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            out.flush();
            in.close();
            out.close();
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }

    @GetMapping(value = "/downZip")
    public Object downZip(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("测试zip下载.zip", "UTF-8"));
        response.setContentType("application/octet-stream");
        File[] files = new File[2];
        files[0] = new File("C:\\Users\\vpclub\\Desktop\\1.png");
        files[1] = new File("C:\\Users\\vpclub\\Desktop\\企业微信截图_20200429180638.png");
        File zip = new File("./哈哈hh.zip");
        zipFiles(files,zip);
        InputStream in = new FileInputStream(zip);
        int i;
        OutputStream out = response.getOutputStream();
        try{
            while ((i = in.read()) != -1) {
                out.write(i);
            }
            out.flush();
            in.close();
            out.close();
        }catch(Exception ex){
            log.error(ex.getMessage(),ex);
        }
        return null;
    }


    //压缩文件
    public static void zipFiles(File[] srcfile, File zipfile){
        ZipOutputStream out = null;
        FileInputStream in = null;
        byte[] buf = new byte[1024];
        try {
            out = new ZipOutputStream(new FileOutputStream(zipfile));
            for (int i = 0; i < srcfile.length; i++) {
                in = new FileInputStream(srcfile[i]);
                out.putNextEntry(new ZipEntry(srcfile[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.closeEntry();
            }
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }finally {
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }

}
