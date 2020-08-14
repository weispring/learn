package com.lxc.learn.netty.tcp;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lixianchun
 * @description
 * @date 2020/8/14
 */
@Slf4j
public class TcpCallHttpProtocolApplicationTest {

    /**
     * 发送HTTP GET与POST格式很重要, 固定的格式如下:
     [REQUEST]<SP><URL><SP>[HTTP VERSION]<CLRF>
     [REQUEST HEADER: ]<SP>[VALUE]<CLRF>
     可以有多个请求头
     最后<CLRF>
     发送完HTTP请求头部以后, 针对不同请求如POST要发送内容部分,发送完成以后同样
     以<CLRF>结尾.
     解释: <SP>表示空格, <CLRF>表示回车换行JAVA中表示为”\r\n”

     REQUEST表示HTTP请求命令,可以为POST, GET, PUT, DELETE等之一
     HTTP VERSION的常见可能值为HTTP/1.1或者HTTP/1.0
     * @param args
     */
    public static void main(String[] args) {
/*        BaseTcp.startClient("www.sina.com.cn", 80, "GET / HTTP/1.1\n" +
                "Host: www.sina.com.cn\n" +
                "Connection: close\n");*/
        //http get
/*        BaseTcp.startClient("localhost", 9998, "GET /test/streamClose?index=11 HTTP/1.1\n" +
                "Host: localhost\n" +
                "Connection: close\n");*/
        //http post 必要的头部Content-Length、Content-Type、头部和请求体的空行
        String json = "{\"id\":100000,\"name\":\"李春\",\"phone\":\"13476670909\"}";
        BaseTcp.startClient("localhost", 9998, "POST /test/testRequestMapping?requestParam=1 HTTP/1.1\n" +
                "Host: localhost\n" +
                "Connection: close\n" +
                "requestHeader: 11\n" +
                "Content-Length: " + json.getBytes().length +"\n" +
                "Content-Type: application/json\n" +
                //分割头部和请求体,此处需要两个换行，一个结束头部，头部下面还有一个空行
                "\r\n" +
                json +
                // 结束标记必须有
                "\r\n"
        );

    }
}
