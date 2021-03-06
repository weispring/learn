package com.lxc.learn.junit;

import com.lxc.learn.common.util.CustomThreadPoolExecutor;
import com.lxc.learn.common.util.DateUtil;
import com.lxc.learn.common.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/6
 */
@Slf4j
public class DailyWorkLog {

    public static void main(String[] args) throws InterruptedException {

        //readNextLine("C:\\Users\\vpclub\\Desktop\\新建 Microsoft Excel 工作表.csv");

        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000), "测试");

        AtomicInteger atomicInteger = new AtomicInteger();

        while (true){
            try {
                customThreadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(atomicInteger.incrementAndGet());
                        HttpClientUtil.invokeGet("http://devcloud.vpclub.cn/umall/common/job/addit/upload1",null, 60000);
                    }
                });
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }


    }



    public static void writeLog(String line){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWJpZCI6NjEzOCwic3ViIjoiMjAxODAzMjkxMDAxNDQiLCJwYXNzd29yZCI6IjI1ZDU1YWQyODNhYTQwMGFmNDY0Yzc2ZDcxM2MwN2FkIiwiaWQiOjg3MDMsImV4cCI6MTU5NjY4MzEzNiwiaWF0IjoxNTk2Njc3MTM2LCJqdGkiOiI3NjhhZmJmNS01YWQ5LTRiYWMtODU3OC04YmExM2U0ODBhZDQiLCJ1c2VybmFtZSI6IjIwMTgwMzI5MTAwMTQ0IiwidG9rZW4iOiJVU0VSIn0.KZF5Y_hByT4Nm0PGwfBey-UnOQiVGGdG5NghjnqpJz8";

        String[] arrays = line.split(",");
        DateUtil.parse(arrays[0],"yyyyMMdd");
        invoke(token,DateUtil.format(DateUtil.parse(arrays[0],"yyyyMMdd"),"yyyy-MM-dd"),arrays[1]);
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void invoke(String token, String date, String content){
        String url = "http://storage.vpclub.cn/app?op=Add";

        Map<String,String> headers = new HashMap();
        headers.put("token",token);
        headers.put("Cookie","_ga=GA1.2.192766667.1577700366; JSESSIONID=B2C50AB369B816CE5DCF2924B6451E55; accountInfo=vphonor%2620180329100144%2625d55ad283aa400af464c76d713c07ad");

        Map<String,String> map = new HashMap();
        map.put("op","Add");
        map.put("key_name",date);
        map.put("department_entity","110");
        map.put("fee_type","1");
        //map.put("workday",null);
        map.put("review_state","1");
        map.put("write_type","2");
        //map.put("share_entities",null);
        map.put("_event","{\"id\":\"EVENT_*_2\",\"remark\":\"\",\"copies\":\"\",\"attachs\":\"\",\"reviewers\":\"\"}");
        map.put("_subtables","{\"work_detail\":[{\"project_num\":3448,\"project_entity\":\"香港umall四期\",\"manager\":8585,\"nature_type\":1,\"project_state\":59,\"manday\":\"1\",\"remark\":"+ "\"" + content + "\"" + ",\"op\":\"Add\"}]}");
        map.put("cloud","work_log");
        map.put("_os","web");
        String result = HttpClientUtil.connectPostHttps(url,headers,map);
        if (com.lxc.learn.common.util.web.StringUtil.isEmpty(result)){
            result = HttpClientUtil.connectPostHttps(url,headers,map);
        }
    }


    public static String readNextLine(String path){
        try {
            FileReader fileReader = new FileReader(new File(path));
            BufferedReader br = new BufferedReader(fileReader);
            String line;
            while ((line = br.readLine()) != null){
                writeLog(line);
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
