package com.lxc.learn.webservice.simple.clien.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 用http 发送webservice 服务请求
 * @author lixianchun
 * @Description
 * @date 2019/6/20 22:25
 */
public class WsUtil {

    private static String url = "http://localhost/user";//"http://localhost:9090/SimpleWebService";


    public static void main(String[] args) {
        invoke(url);
    }


    public static String invoke(String req){
        StringBuffer sb = new StringBuffer();
        try {
            String urlStr = url;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            OutputStream os = con.getOutputStream();
            //下面这行代码是用字符串拼出要发送的xml，xml的内容是从测试软件里拷贝出来的
            //需要注意的是，有些空格不要弄丢哦，要不然会报500错误的。
            //参数什么的，你可以封装一下方法，自动生成对应的xml脚本
            String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
                    "<soap:Envelope "+
                    "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+


                    "<soap:Body>"+
                    "<simple xmlns=\"http://serve.simple.webservice.learn.lxc.com/\">"+
                    "<name>12345678</name>"+ //你的qq号填到里边
                    "<address>12345678</address>"+
                    "<id>12345678</id>"+
                    "</simple> "+
                    "</soap:Body>"+
                    "</soap:Envelope>";

            soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
                    "<soap:Envelope "+
                    "xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+

                    "<soap:Header>" +
                    "<username>" +
                    "root" +
                    "</username>" +
                    "<password>admin</password>" +

                    "</soap:Header>"+
                    "<soap:Body>"+
                    "<ns2:getUser xmlns:ns2=\"http://service.webservice.liyulin.com/\"><arg0>maple</arg0></ns2:getUser>"+
                    "</soap:Body>"+
                    "</soap:Envelope>";

            os.write(soap.getBytes());
            os.close();

            InputStream is = con.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);

            int tempChar;
            char[] chars = new char[1024];
            while((tempChar = reader.read(chars,0,chars.length)) != -1){
                new String(chars,0,tempChar);
                sb.append(chars,0,tempChar);
            }
            //下面这行输出返回的xml到控制台，相关的解析操作大家自己动手喽。
            //如果想要简单的话，也可以用正则表达式取结果出来。
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>> returnedxmlstr: "+sb.toString());
            os.close();
            is.close();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



}
