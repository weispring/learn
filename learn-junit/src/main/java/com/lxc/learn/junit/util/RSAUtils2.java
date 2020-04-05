package com.lxc.learn.junit.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;



public class RSAUtils2 {

    /**
     * RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024
     */
    public static final int KEY_SIZE = 2048;

    //公钥
    private final static String publicKeytest = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAroKCNZIu7LkPor9XNx7+0W0O0ykzqUkWPi6kDcE3atti/LgXmCvRc052S6hzkVVb2wFmfcRaAnYmQRRkmWCTUuZKu/R4jq2wqb8apERblrALeQFnfHf71S5A6luhMew3aLrZ8hvbnkZkBYGNpBFa+b2Q3i1x71EMS3I2pPSjJ8n+sqEtJa0zn91zIF950ahBfoAurxN4k2ZfdB94x+6b7GT31nygf3cSbLIuvumN5lZAnbp+lCQVi69usZ+UTEXyY/6cQvAy5G1WL8NwcA72wGMult2n4/1tNGPWWXvBye4g/yGyNXBB8UzasO0aKesW2kU9bKsBmSvEXkmL5Bsi8wIDAQAB";

    private final static String url = "http://devcloud.vpclub.cn/umall/external/services/external/write";

    private final static String md5Key = "d2s6y8ik9lg64f5";

    private final static String appKey = "d2s6y8ik9lg64f35";

    private final static String charset = "utf-8";
    private final static String secret = "22222";



    public static void main(String[] args) throws Exception {
        Storer storer = new Storer();
        VPclubRSAUtils vPclubRSAUtils = new VPclubRSAUtils();
        storer.setAppKey(appKey);
        //1获取token后进行写卡
        storer.setUserId("1139361991996465152");
        storer.setIccid("89852121510287405658");
        storer.setOrderId(1246423179417899010L);
        storer.setShopCode("shop code");
        storer.setPOSCode("POS code");
        storer.setAccountCode("account code");
        storer.setAccountNumber("account number");
        storer.setMethod(0);
        storer.setTimestamp(System.currentTimeMillis());
        storer.setDigest(encode(appKey + storer.getTimestamp() + secret, secret));
        storer.setVersions("1.0.0");
        System.out.println("content:" + storer.toString());
        String s = JSONObject.toJSONString(storer);
        System.out.println("" + s);
        //long temp = System.currentTimeMillis();
        String messageEn = vPclubRSAUtils.encipher(s, publicKeytest);
        System.out.println("" + messageEn);
        //System.out.println("time lapse:" + (System.currentTimeMillis() - temp) / 1000.0 + "");
        Encryption encryption = new Encryption();
        encryption.setData(messageEn);
        String s2 = JSONObject.toJSONString(encryption);
        System.out.println("加密后的入参" + s2.toString());
        String s1 = postJsonRturnJson(url, s2, "zh-US");
        System.out.println("output:" + s1);

    }

    //MD5加密处理
    public static String encode(String src, String key) {
        try {
            //得到一个指定的编码格式的字节数组，Linux和windows默认的编码格式不同，所以要指定特定的编码
            byte[] data = src.getBytes(charset);
            byte[] keys = key.getBytes();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                //结合key和相应的数据进行加密操作,ofxx的作用是补码，byte是8bits，而int是32bits
                int n = (0xff & data[i]) + (0xff & keys[i % keys.length]);
                sb.append("S").append(n);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return src;
    }


    /**
     * 发送Json返回Json
     *
     * @param url
     * @param message
     * @return 方法捕获异常，并返回空串
     */
    public static String postJsonRturnJson(String url, String message, String language) {

        CloseableHttpClient httpClient = getCloseableHttpClient();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            if (null != language && !"".equals(language)) {
                httpPost.setHeader("Accept-Language", language);
            }
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-Type", "application/json");
            StringEntity stringEntity = new StringEntity(message, "UTF-8");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    // 连接池管理器
    private static final PoolingHttpClientConnectionManager poolConnManager =
            new PoolingHttpClientConnectionManager();
    // retry handler
    private static final HttpRequestRetryHandler httpRequestRetryHandler =
            new HttpRequestRetryHandler() {
                public boolean retryRequest(
                        IOException exception, int executionCount, HttpContext context) {
                    if (executionCount >= 5) {
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {
                        return true;
                    }
                    if (exception instanceof InterruptedIOException) {
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {
                        return false;
                    }
                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    return !(request instanceof HttpEntityEnclosingRequest);
                }
            };

    static {
        // 类加载的时候 设置最大连接数
        poolConnManager.setMaxTotal(100);
        // 每个路由的最大连接数
        poolConnManager.setDefaultMaxPerRoute(100);
    }

    private static CloseableHttpClient getCloseableHttpClient() {
        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .setConnectionManager(poolConnManager)
                        .setRetryHandler(httpRequestRetryHandler)
                        .build();

        return httpClient;
    }


    public static class Encryption implements Serializable {
        private String data;



        public void setData(String data) {
            this.data = data;
        }
    }



    public static class Storer {
        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public String getVersions() {
            return versions;
        }

        public void setVersions(String versions) {
            this.versions = versions;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public String getShopCode() {
            return shopCode;
        }

        public void setShopCode(String shopCode) {
            this.shopCode = shopCode;
        }

        public String getPOSCode() {
            return POSCode;
        }

        public void setPOSCode(String POSCode) {
            this.POSCode = POSCode;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getIccid() {
            return iccid;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public int getMethod() {
            return method;
        }

        public void setMethod(int method) {
            this.method = method;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        private String userId;
        private long orderId;
        private String appKey;
        private String iccid;
        private int method;
        /**
         *
         */
        private String POSCode;
        /**
         * 便利店code
         */
        private String shopCode;
        /**
         * 操作人账号
         */
        private String accountNumber;
        /**
         * 操作人编号
         */
        private String accountCode;

        private long timestamp;
        private String versions;
        private String digest;

    }


}