package com.lxc.learn.junit;

import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @description
 * @date 2020/7/1
 */
@Slf4j
public class Main {

    /**
     *
     */

    public static void main(String[] args) throws IOException, InterruptedException {
/*        String a = "http://127.0.0.1:8089/test/testKong?id=4000";
        HttpClientUtil.invokeGet(a, null, 3000);
        System.out.println(a);*/
        String parent = "C:\\Users\\vpclub\\Desktop\\666";
        System.out.println(parent);
        File file = new File(parent);
        while (true){
            for (File f : file.listFiles()){
                try{
                    HttpClientUtil.upload("http://172.16.13.43:8080/test/uploadPict", null, null, f, "fileData");
                }catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        }

       /* while (true){
            try {
                String url = "http://devcloud.vpclub.cn/umall/attachment/consumer/paperless/getContract?orderId=1305790742211002369";
                HttpClientUtil.invokeGet(url, null, 60000);
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }finally {
                //Thread.sleep(60*1000);
            }
        }*/

       /* Calendar calendar = Calendar.getInstance();
        log.info("{} {} {}", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        System.out.println(DateUtil.format(new Date(), "yyyyMMdd"));

        Calendar quarterCalendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        System.out.println(quarterCalendar.getTime().getTime());

        String d = DateUtil.format(new Date(), "yyyyMMdd");
        Date date = DateUtil.parse(d, "yyyyMMdd");
        System.out.println(date.getTime());*/


 /*      String a = "https://umall.hk.chinamobile.com/group1/M00/0E/70/CgADU17yxTGAceZNAABV_YVu_Hg141.png";
       a = a.replaceFirst("https://umall.hk.chinamobile.com/group1","http://fastdfs-storage");
       System.out.println(a);*/
    }

}
