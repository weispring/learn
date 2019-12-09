package com.lxc.learn.common.util;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import java.net.UnknownHostException;

/**
 * Http Client 請求工具,提升性能
 * 
 * @author INS7000
 *
 */
public class WebTools {

	public static String KEY_STATUS_CODE = "statusCode";
	public static String KEY_CONTENT = "content";
	// 连接池管理器
	private final static PoolingHttpClientConnectionManager poolConnManager = new PoolingHttpClientConnectionManager();
	// retry handler
	private final static HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
		public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
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
			if (!(request instanceof HttpEntityEnclosingRequest)) {
				return true;
			}
			return false;
		}
	};

	static {
		//类加载的时候 设置最大连接数 
		poolConnManager.setMaxTotal(100);
		//每个路由的最大连接数
		poolConnManager.setDefaultMaxPerRoute(100);
	}

	public static CloseableHttpClient getCloseableHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolConnManager)
				.setRetryHandler(httpRequestRetryHandler).build();

		return httpClient;
	}

	/**
	 * send json by post method
	 *
	 * @param url
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String postJson(String url, String message) {

		CloseableHttpClient httpClient = getCloseableHttpClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");
			StringEntity stringEntity = new StringEntity(message,"UTF-8");
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


	public static String get(String url) {

		CloseableHttpClient httpClient = getCloseableHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader("Connection", "keep-alive");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
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
}
