package com.lxc.learn.common.util;

import com.lxc.learn.common.constant.SystemConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@Slf4j
public class HttpClientUtil {
    private final static int connectTimeout = 8000;
    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;
    private static CloseableHttpClient proxyHttpclient = null;

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", PlainConnectionSocketFactory.INSTANCE).build();
        connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpclient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000).setSocketTimeout(30000).build()).setConnectionManager(connManager).build();/* Create socket configuration*/
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        connManager.setDefaultSocketConfig(socketConfig);/* Create message constraints*/
        MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200).setMaxLineLength(2000).build();/* Create connection configuration*/
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8).setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(200);
        connManager.setDefaultMaxPerRoute(20);

        if (System.getProperty("httpProxyPort") != null){
            proxyHttpclient = HttpClients.custom().setDefaultRequestConfig(RequestConfig.custom().setConnectionRequestTimeout(5000).setConnectTimeout(10000).setSocketTimeout(30000)
                    .setProxy(new HttpHost(System.getProperty("httpProxyHost"),Integer.valueOf(System.getProperty("httpProxyPort")))).build())
                    .setConnectionManager(connManager).build();/* Create socket configuration*/
        }


    }
    public static String postJsonBody(String url, Object object){
        return postJsonBody(url,object, SystemConstant.UTF8);
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
            if (object instanceof String){
                str1 = (String) object;
            }

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
            log.error("exception", e);
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

    @SneakyThrows
    public static byte[] invokeGet(String url, String encode, int connectTimeout) {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();

        log.info("[HttpUtils Get] begin invoke:" + url);
        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);
        CloseableHttpResponse response = httpclient.execute(get);
        try {
            HttpEntity entity = response.getEntity();
            try {
                if (entity != null) {
                    return EntityUtils.toByteArray(entity);
                }
            } finally {
                if (entity != null) entity.getContent().close();
            }
        } catch (Exception e) {
            log.error(String.format("[HttpUtils Get]get response error, url:%s", url), e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * HTTPS请求，默认超时为5S
     */
    public static String connectPostHttps(String reqURL,Map<String,String> headers, Map<String, String> params) {
        String responseContent = null;
        HttpPost httpPost = new HttpPost(reqURL);
        try {
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeout).setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectTimeout).build();
            List<NameValuePair> formParams = new ArrayList<>();
            for (Entry<String, String> entry : params.entrySet()){
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                log.info("{}:{}",entry.getKey(),entry.getValue());
            }

            httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
            httpPost.setConfig(requestConfig);/* 绑定到请求 Entry*/


            for (Map.Entry<String,String> entry : headers.entrySet()){
                httpPost.setHeader(entry.getKey(),entry.getValue());
            }


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


    public static String upload(String url, Map<String, String> header, Map<String, Object> body, File in, String fileName) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("accept", "*/*");
        httpPost.setHeader("connection", "Keep-Alive");
        httpPost.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        //httpPost.setHeader("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        if (null != header) {
            Iterator var6 = header.entrySet().iterator();
            while(var6.hasNext()) {
                Entry<String, String> entry = (Entry)var6.next();
                httpPost.setHeader((String)entry.getKey(), (String)entry.getValue());
            }
        }
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
        //解决上传文件名 乱码
        multipartEntity.setCharset(Charset.forName("utf-8"));
        multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (body != null) {
            Iterator var13 = body.entrySet().iterator();
            while (var13.hasNext()) {
                Entry<String, Object> entry = (Entry) var13.next();
                multipartEntity.addPart((String) entry.getKey(), new StringBody(entry.getValue().toString(), ContentType.TEXT_PLAIN));
            }
        }
        if (in != null) {
            ContentBody contentBody = new FileBody(in, ContentType.MULTIPART_FORM_DATA);
            multipartEntity.addPart(fileName, contentBody);
        }
        httpPost.setEntity(multipartEntity.build());
        try {
            HttpResponse httpResponse = httpclient.execute(httpPost);
            return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }



    public static boolean down(String url, Map<String,String> header, String parentPath, String fileName){
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("accept", "*/*");
        httpGet.setHeader("connection", "Keep-Alive");
        httpGet.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

        if (null != header) {
            Iterator var6 = header.entrySet().iterator();
            while(var6.hasNext()) {
                Entry<String, String> entry = (Entry)var6.next();
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            HttpResponse httpResponse = httpclient.execute(httpGet);
            InputStream is = httpResponse.getEntity().getContent();
            if (!new File(parentPath).exists()){
                new File(parentPath).mkdirs();
            }
            File file = new File(parentPath + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int n;
            while ((n=is.read(bytes)) != -1){
                fos.write(bytes,0,n);
            }
            fos.flush();
            is.close();
            return true;
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return false;
    }


    /**
     * URLConnection 发送http请求
     * 可设置请求方法、header、参数
     * @param url
     * @param param
     * @param connectTimeout
     * @param readTimeout
     * @param headerMap
     * @return
     */
    public static String sendPost(String url, String param, int connectTimeout, int readTimeout, Map<String, String> headerMap) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (null != headerMap && !headerMap.isEmpty()) {
                Iterator var10 = headerMap.entrySet().iterator();

                while(var10.hasNext()) {
                    Entry<String, String> entry = (Entry)var10.next();
                    if (!StringUtils.isEmpty((String)entry.getValue())) {
                        conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
                    }
                }
            }

            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            /*osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            osw.write(paramStr);*/
            out.flush();

            String line;
            for(in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); (line = in.readLine()) != null; result = result + line) {
                ;
            }
        } catch (Exception var19) {
            result = "ERROR";
            throw new RuntimeException("sendPost method error: "+ var19.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }

                if (in != null) {
                    in.close();
                }
            } catch (IOException var18) {
                log.error("sendPost method close PrintWriter or BufferedReader " + var18);
            }

        }

        return result;
    }


    public static String sendHttpPostWithProxy(String httpUrl, String params, HttpHost proxy, String contentType, String charset) {
        if (null != proxy) {
            log.info("开启代理");
            httpclient.getParams().setParameter("http.route.default-proxy", proxy);
        }

        HttpPost httpPost = new HttpPost(httpUrl);
        HttpEntity entity = null;
        String responseContent = null;
        CloseableHttpResponse response = null;

        try {
            StringEntity stringEntity = new StringEntity(params, StringUtils.isEmpty(charset) ? "UTF-8" : charset);
            stringEntity.setContentType(StringUtils.isEmpty(contentType) ? "application/x-www-form-urlencoded" : contentType);
            httpPost.setEntity(stringEntity);
            response = httpclient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception var14) {
            var14.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        return responseContent;
    }


    public static byte[] getReturnByte(String url) {
        CloseableHttpClient httpClient = WebTools.getCloseableHttpClient();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toByteArray(responseEntity);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
        return null;
    }
}