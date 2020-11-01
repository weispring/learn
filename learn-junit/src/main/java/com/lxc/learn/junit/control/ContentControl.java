package com.lxc.learn.junit.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.content.text.plain;

/**
 * @author lixianchun
 * @description
 * @date 2020/11/1
 */
@Slf4j
@RequestMapping(value = "/content")
@RestController
public class ContentControl {

    /**
     * Content-Type
     * 不同的媒体类型，浏览器会做不同处理
     * 如：text/plain和text/html，相同的报文，前者浏览器不会解析为html,后者会。
     * @return
     */

    @RequestMapping(value = "/text", produces = {MediaType.TEXT_PLAIN_VALUE})
    public String contentText(){
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>我的第一个 HTML 页面</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<p>body 元素的内容会显示在浏览器中。</p>\n" +
                "<p>title 元素的内容会显示在浏览器的标题栏中。</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }

    @RequestMapping(value = "/html", produces = {MediaType.TEXT_HTML_VALUE})
    public String contentHtml(){
        return "<html>\n" +
                "\n" +
                "<head>\n" +
                "<title>我的第一个 HTML 页面</title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<p>body 元素的内容会显示在浏览器中。</p>\n" +
                "<p>title 元素的内容会显示在浏览器的标题栏中。</p>\n" +
                "</body>\n" +
                "\n" +
                "</html>\n";
    }
}
