package com.lxc.learn.common.util.xml;

import org.apache.http.HttpRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Description
 * @Author lixianchun
 * @Date 2021/7/9
 **/
public class HttpRequestRetryHandlerCustom extends DefaultHttpRequestRetryHandler {

    private int retryCount = 0;
    private boolean requestSentRetryEnabled = false;

    public HttpRequestRetryHandlerCustom(int retryCount, boolean requestSentRetryEnabled){
        super(retryCount, requestSentRetryEnabled);
        this.requestSentRetryEnabled = requestSentRetryEnabled;
        this.retryCount =retryCount;
    }


    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        Args.notNull(exception, "Exception parameter");
        Args.notNull(context, "HTTP context");
        if (executionCount > this.retryCount) {
            return false;
        } else {
            if (this.requestSentRetryEnabled) {
                return true;
            }
        }
        return false;
    }

}
