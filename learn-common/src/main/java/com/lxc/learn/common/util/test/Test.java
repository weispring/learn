package com.lxc.learn.common.util.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/18 19:56
 */
public class Test {
    public static void main(String[] args) throws Exception {

        List list = null;

        List<String> list1 = list;


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    invoke();
                    try {
                        Thread.sleep(1000*60*3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

   public static void invoke(){
        try{
            String uri = "http://devcloud.vpclub.cn/umall/common/job/coupon/deleteContract";

            HttpURLConnection connection = (HttpURLConnection)new URL(uri).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestMethod("POST");
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print("{\n" +
                    "\t\"id\":\"1\"\n" +
                    "}");
            /*osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(paramStr);*/
            out.flush();
            BufferedReader in = null;
            String line;
            String result = "";
            for(in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")); (line = in.readLine()) != null; result = result + line) {
                ;
            }
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
   }
}
