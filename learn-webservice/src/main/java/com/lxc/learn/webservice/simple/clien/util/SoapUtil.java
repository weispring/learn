package com.lxc.learn.webservice.simple.clien.util;

/**
 * @author lixianchun
 * @Description
 * @date 2019/12/9 15:59
 */

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.apache.http.HttpHost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

@Slf4j
public class SoapUtil {

/*    HttpHost proxy = new HttpHost(proxyHost,port);
    DefaultHttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);*/
    public static void main(String[] args) {

        String url = "http://172.16.46.4:7073/HKBNWSCmhk/ServiceOrderWS";

        String reqXml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                "<SOAP-ENV:Header/>" +
             /*   "<username>" +
                "webserviceuser" +
                "</username>" +
                "<password>welcome1</password>" +

                "</SOAP-ENV:Header/>"+*/

                "<SOAP-ENV:Body>" +
                "<ns3:getAddressDetailsByCode  xmlns:ns3=\"http://ws.hkbn.com.hk/\">" +
                "<addressCode>81780</addressCode></ns3:getAddressDetailsByCode></SOAP-ENV:Body></SOAP-ENV:Envelope>";
        responseSoap(reqXml, url, "utf-8", "text/xml");

    }


    /**
     * <p>Description: 根据请求报文，请求服务地址获取 响应报文
     * @param requestSoap  请求报文
     * @param serviceAddress 请求地址
     * @param charSet 字符集  utf-8
     * @param contentType  类型  text/xml; charset=utf-8
     * @return  map封装的 服务器响应参数和返回报文.PS:statusCode :200正常响应。responseSoap：响应报文
     * <p>thinking: </p>
     *
     * @author
     */
    public  static Map<String,Object> responseSoap(String requestSoap,String serviceAddress,String charSet, String contentType){
        String responseSoap="";
        Map<String,Object> resultmap=new HashMap<String,Object>();
        PostMethod postMethod = new PostMethod(serviceAddress);
        HttpClient httpClient = new HttpClient();
        //HTTPSSecureProtocolSocketFactory
 /*       Protocol myhttps = new Protocol("https", new SSLProtocolSocketFactory(), 443);//支持https
        Protocol.registerProtocol("https", myhttps);*/
        int statusCode = 0;
        try {
            //两种方式 实现webservice认证访问接口
            //1. UsernamePasswordCredentials
            //2. httpHeader authorization Basic Auth
            httpClient.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("webserviceuser", "welcome1"));//设置用户名密码,如果不需要就忽略这一行
            StringRequestEntity entity = new StringRequestEntity(requestSoap,contentType,charSet);
            //postMethod.setRequestHeader("authorization", "Basic d2Vic2VydmljZXVzZXI6d2VsY29tZTE=");
            postMethod.setRequestEntity(entity);
            statusCode = httpClient.executeMethod(postMethod);

            resultmap.put("statusCode", statusCode);
        } catch (IOException e) {
            throw new RuntimeException("执行http请求失败", e);
        }
        try {
            responseSoap = postMethod.getResponseBodyAsString();
            log.info("返回：{}", responseSoap);
        } catch (IOException e) {
            throw new RuntimeException("获取请求返回报文失败", e);
        }
        if (statusCode == 200) {
            try {
                responseSoap = postMethod.getResponseBodyAsString();
                resultmap.put("responseSoap", responseSoap);
            } catch (IOException e) {
                throw new RuntimeException("获取请求返回报文失败", e);
            }
        } else {
            throw new RuntimeException("请求失败：" + statusCode);
        }
        return resultmap;
    }

}
