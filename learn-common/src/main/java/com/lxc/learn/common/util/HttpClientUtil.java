package com.lxc.learn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Slf4j
public class HttpClientUtil {
    private final static int connectTimeout = 8000;
    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).build();
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpclient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000).setSocketTimeout(30000).build()).setConnectionManager(connManager).build();/* Create socket configuration*/
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);/* Create message constraints*/
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();/* Create connection configuration*/
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);
    }

    /**
     * RedirectExec RetryExec ProtocolExec MainClientExec
     * 通过route从连接池中获取连接 HttpRequestExecutor
     *
     * @param url @param object @param encoding @return
     */
    public static String postJsonBody(String url, Object object, String encoding) {
        Long start = System.currentTimeMillis();
        HttpPost post = new HttpPost(url);
        try {
            post.setHeader("Content-type", "application/json");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).setExpectContinueEnabled(true).build();/*post.setConfig(requestConfig);*/
            String str1 = JsonUtil.objectToJson(object);
            post.setEntity(new StringEntity(str1, encoding));
            log.info("[HttpUtils Post] begin invoke url:" + url + " , params:" + str1);
            CloseableHttpResponse response = httpclient.execute(post);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        String str = EntityUtils.toString(entity, encoding);
                        log.info("[HttpUtils Post]Debug response, url :" + url + " , response string :" + str);
                        return str;
                    }
                } finally {
                    if (entity != null) entity.getContent().close();
                }
            } finally {
                if (response != null) response.close();
            }
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException", e);
        } catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            log.info("耗时：{}", (System.currentTimeMillis() - start) / 1000);
            post.releaseConnection();
        }
        return "";
    }

    @SuppressWarnings("deprecation")
    public static String invokeGet(String url, Map<String, String> params, String encode, int connectTimeout, int soTimeout) {
        String responseString = null;
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        int i = 0;
        for (Entry<String, String> entry : params.entrySet()) {
            if (i == 0 && !url.contains("?")) sb.append("?");
            else sb.append("&");
            sb.append(entry.getKey());
            sb.append("=");
            String value = entry.getValue();
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.warn("encode http get params error, value is " + value, e);
                sb.append(URLEncoder.encode(value));
            }
            i++;
        }
        log.info("[HttpUtils Get] begin invoke:" + sb.toString());
        HttpGet get = new HttpGet(sb.toString());
        get.setConfig(requestConfig);
        try {
            CloseableHttpResponse response = httpclient.execute(get);
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) responseString = EntityUtils.toString(entity, encode);
                } finally {
                    if (entity != null) entity.getContent().close();
                }
            } catch (Exception e) {
                log.error(String.format("[HttpUtils Get]get response error, url:%s", sb.toString()), e);
                return responseString;
            } finally {
                if (response != null) response.close();
            }
            log.info(String.format("[HttpUtils Get]Debug url:%s , response string %s:", sb.toString(), responseString));
        } catch (SocketTimeoutException e) {
            log.error(String.format("[HttpUtils Get]invoke get timout error, url:%s", sb.toString()), e);
            return responseString;
        } catch (Exception e) {
            log.error(String.format("[HttpUtils Get]invoke get error, url:%s", sb.toString()), e);
        } finally {
            get.releaseConnection();
        }
        return responseString;
    }

    /**
     * HTTPS请求，默认超时为5S
     */
    public static String connectPostHttps(String reqURL, Map<String, String> params) {
        String responseContent = null;
        HttpPost httpPost = new HttpPost(reqURL);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
            List<NameValuePair> formParams = new ArrayList<>();
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
            httpPost.setConfig(requestConfig);/* 绑定到请求 Entry*/
            for (Entry<String, String> entry : params.entrySet())
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                /* 执行POST请求*/
                HttpEntity entity = response.getEntity();
                /* 获取响应实体*/
                try {
                    if (null != entity) responseContent = EntityUtils.toString(entity, Consts.UTF_8);
                } finally {
                    if (entity != null) entity.getContent().close();
                }
            } finally {
                if (response != null) response.close();
            }
            log.info("requestURI : " + httpPost.getURI() + ", responseContent: " + responseContent);
        } catch (ClientProtocolException e) {
            log.error("ClientProtocolException", e);
        } catch (IOException e) {
            log.error("IOException", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;
    }
}