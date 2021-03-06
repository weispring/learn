package com.lxc.learn.junit.control;

import com.lxc.learn.common.constant.SystemConstant;
import com.lxc.learn.common.util.JsonUtil;
import com.lxc.learn.common.util.WebUtil;
import com.lxc.learn.common.util.core.Resp;
import com.lxc.learn.common.util.core.RespUtil;
import com.lxc.learn.jdk.image.PictureUtil;
import com.lxc.learn.junit.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
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

    @Autowired
    private Environment environment;
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


    /**
     * 重定向的实现：设置状态码和头部Location
     *
     * setStatus(status);
     setHeader("Location", locationUri);
     */
    @RequestMapping(value = "/httpRefresh")
    public Object httpRefresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("请求入参：{}","");
        //Refresh 替代Retry-After refresh /应用于重定向或一个新的资源被创造，在5秒之后重定向（由网景提出，被大部分浏览器支持）
        response.setHeader("Refresh","5;url=http://localhost:9999/httpRefresh");
        System.out.println(System.getProperty("spring.devtools.restart.enabled"));
        System.out.println(environment.getProperty("spring.devtools.restart.enabled"));
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

    @RequestMapping(value = "/streamClose")
    public Resp upload(User user, HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info("index:{}",request.getParameter("index"));
        response.getOutputStream().write("测试streamClosestreamClosestreamClose".getBytes());
        //需要刷新缓冲
        response.getOutputStream().flush();
        //若不关闭，返回值也会写道输入流中
        response.getOutputStream().close();
        //关闭后不会再写，没有报错？
        response.getOutputStream().write("测试streamClosestreamClosestreamClose".getBytes());
        return RespUtil.convertResult(true);
    }


    /**
     * 要求输入 输入指定头部、请求参数、内容类型
     * @param user
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/testRequestMapping",headers = {"requestHeader"},params = {"requestParam"},consumes = {"application/json"},produces = {"application/json"})
    public Resp testRequestMapping(@RequestBody User user, HttpServletResponse response){
        log.info("{}", JsonUtil.objectToJson(user));
        return RespUtil.convertResult(true);
    }

    @RequestMapping(value = "/testGet",method = RequestMethod.GET)
    public Object testGet(HttpServletRequest request, HttpServletResponse response){
        log.info("请求入参：{}","");
        Map map = WebUtil.getRequestParams(request);
        map.put("Connection", "keep-alive");
        map.put("Retry-After", "5");
        return RespUtil.convertResult(true);
    }


    @RequestMapping(value = "/header1")
    public Resp header1(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Date",1999);
        return RespUtil.convertResult(true);
    }


    @RequestMapping(value = "/header2")
    public ResponseEntity header2(HttpServletRequest request, HttpServletResponse response){
        HttpHeaders headers = new HttpHeaders();
        headers.add("head1", "head1value");
        headers.add("Content-Type", "application/x-javascript; charset=gb2312");
        return ResponseEntity.status(200).headers(headers).body("this is body");
    }



    @RequestMapping(value = "/header3")
    public void header3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("content-type", "application/x-javascript; charset=gb2312");
        response.setHeader("selfHeader","selfHeaderValue");
        response.getOutputStream().print("this is body");
    }


    @RequestMapping(value = "/testOut",method = RequestMethod.GET)
    public Resp testOut(HttpServletRequest request,HttpServletResponse response) throws IOException {
        ServletOutputStream writer = response.getOutputStream();
        //不然会乱码
        response.setHeader("content-type", "text/plain;charset=utf-8");
        PrintWriter pw = new PrintWriter(writer);
        pw.write("首行");
        pw.write("第二行\n");
        pw.write("第三行");
        pw.write("\r\n");
        pw.write("第四行");
        pw.write("\r\n");
        pw.flush();
        //若关闭流，则Resp 不会输出
        pw.close();
        return RespUtil.convertResult(true);
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
