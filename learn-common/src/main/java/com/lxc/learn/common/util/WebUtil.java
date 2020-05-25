package com.lxc.learn.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lixianchun
 * @Description
 * @date 2019/7/3 20:14
 */

public final class WebUtil {
    private static final Logger log = LoggerFactory.getLogger(WebUtil.class);

    public WebUtil() {
    }

    public static String unhtml(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        } else {
            String html = content.replaceAll("'", "&apos;");
            html = html.replaceAll("\"", "&quot;");
            html = html.replaceAll("\t", "&nbsp;&nbsp;");
            html = html.replaceAll("<", "&lt;");
            html = html.replaceAll(">", "&gt;");
            return html;
        }
    }

    public static String html(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        } else {
            String html = content.replaceAll("&apos;", "'");
            html = html.replaceAll("&quot;", "\"");
            html = html.replaceAll("&nbsp;", " ");
            html = html.replaceAll("&lt;", "<");
            html = html.replaceAll("&gt;", ">");
            return html;
        }
    }

    public static String getWebPath(HttpServletRequest request, String relativePath) {
        return relativePath.charAt(0) == '/' ? getWebRootPath(request) + relativePath.substring(1) : getWebRootPath(request) + relativePath;
    }

    public static String getWebRootPath(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getSession().getServletContext().getContextPath()).append("/").toString();
        return tempContextUrl;
    }

    public static String getIpAddr(HttpServletRequest request) {
        //x-forwarded-for 大小写不影响，应该是统一全部转换了
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inet = null;

                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException var4) {
                    log.error(var4.getMessage(), var4);
                }

                ipAddress = inet.getHostAddress();
            }
        }

        if (ipAddress != null && ipAddress.indexOf(",") > 0) {
            ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
        }

        return ipAddress;
    }

    public static Map<String, String> getRequestParamsAndLog(Logger log, HttpServletRequest request) {
        String requsetIp = getIpAddr(request);
        StringBuffer sb = new StringBuffer();
        sb.append("\n------------------------------------------\n");
        sb.append(",requestURL=" + request.getRequestURL());
        sb.append(",reuestIp=" + requsetIp);
        sb.append(",requestData=");
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, String> changeMap = new HashMap();
        if (requestMap != null && requestMap.keySet().size() > 0) {
            Iterator var6 = requestMap.keySet().iterator();

            while(var6.hasNext()) {
                String key = (String)var6.next();
                changeMap.put(key, ((String[])requestMap.get(key))[0]);
            }
        }

        sb.append(JSON.toJSON(requestMap));
        sb.append("\n------------------------------------------\n");
        if (log != null) {
            log.info(sb.toString());
        }

        request.setAttribute("requsetIp", requsetIp);
        return changeMap;
    }

    public static Map<String, String> getRequestParams(HttpServletRequest request) {
        request.setAttribute("requsetIp", getIpAddr(request));
        Map<String, String[]> requestMap = request.getParameterMap();
        if (requestMap != null && requestMap.keySet().size() > 0) {
            Map<String, String> changeMap = new HashMap();
            Iterator var3 = requestMap.keySet().iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                changeMap.put(key, ((String[])requestMap.get(key))[0]);
            }

            return changeMap;
        } else {
            return null;
        }
    }

    public static String getRequestBodyStrAndResponse(HttpServletRequest request, HttpServletResponse response, String responseStr) {
        writeOutResponse(response, responseStr);
        return getRequestBodyStr(request);
    }

    public static void writeOutResponse(HttpServletResponse response, String responseStr) {
        try {
            PrintWriter out = response.getWriter();
            out.write(responseStr);
        } catch (IOException var3) {
            log.error(var3.getMessage(), var3);
        }

    }

    public static String getRequestBodyStr(HttpServletRequest request) {
        String result = "";

        try {
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            Throwable var3 = null;

            try {
                InputStream inStream = request.getInputStream();
                byte[] buffer = new byte[1024];
                boolean var6 = false;

                int len;
                while((len = inStream.read(buffer)) != -1) {
                    outSteam.write(buffer, 0, len);
                }

                result = new String(outSteam.toByteArray(), "utf-8");
            } catch (Throwable var15) {
                var3 = var15;
                throw var15;
            } finally {
                if (outSteam != null) {
                    if (var3 != null) {
                        try {
                            outSteam.close();
                        } catch (Throwable var14) {
                            var3.addSuppressed(var14);
                        }
                    } else {
                        outSteam.close();
                    }
                }

            }
        } catch (IOException var17) {
            log.error(var17.getMessage(), var17);
        }

        return result;
    }

    public static JSONObject getRequestParamsFromBodyStr(HttpServletRequest request) {
        String str = getRequestBodyStr(request);

        try {
            return str.startsWith("{") ? JSONObject.parseObject(str) : null;
        } catch (Exception var3) {
            return null;
        }
    }

    public static String getRequestQueryStr(Logger log, HttpServletRequest request) throws IOException {
        String submitMehtod = request.getMethod();
        return submitMehtod.equalsIgnoreCase("post") ? getRequestPostStr(log, request) : request.getQueryString();
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        } else {
            byte[] buffer = new byte[contentLength];

            int readlen;
            for(int i = 0; i < contentLength; i += readlen) {
                readlen = request.getInputStream().read(buffer, i, contentLength - i);
                if (readlen == -1) {
                    break;
                }
            }

            return buffer;
        }
    }

    public static String getRequestPostStr(Logger log, HttpServletRequest request) throws IOException {
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Character Encoding: {}", request.getCharacterEncoding());
        log.info("Content Length: {}", request.getContentLength());
        log.info("Content Type: {}", request.getContentType());
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }

        return buffer == null ? null : new String(buffer, charEncoding);
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession();
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        } else {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
            return servletRequestAttributes.getRequest();
        }
    }

    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        } else {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
            return servletRequestAttributes.getResponse();
        }
    }

    public static void main(String[] args) {
        String str = Base64.getEncoder().encodeToString("webserviceuser:welcome1".getBytes());

        log.info("{}", str);
    }
}

