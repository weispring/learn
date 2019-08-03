package com.lxc.learn.junit.test.api;

import com.lxc.learn.common.util.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lixianchun
 * @Description
 * @date 2019/8/2 17:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j
public class EmailTest {

    @Autowired
    private EmailService emailService;



    /**
     * 1.特殊字符&有时会乱码
     * 2.采用html转义字符，url后面4个参数，中间那个& 任乱码
     * *
     * 邮件发送特殊字符 显示乱码问题 转换为html文档后，就是html特殊字符的处理方式

     */
    @Test
    public void testEmail() throws Exception{

        String content = "http://172.16.46.4:50100/cmhk/client/downloadContractDoc.action?";
        String path =  "paramMap.isEncryption=1&paramMap.key=92048181&paramMap.isMerger=1&paramMap.serialId=UM20190731223295";
        content = content + path;
        log.info("{}", content);
        String own = "http://172.16.46.4:50100/cmhk/clien/downloadContractDoc.action?paramMap.isEncryption=1&paramMap.key=92048181&paramMap.isMerger=1&paramMap.serialId=UM20190731223295";
        content = content + "<br>" + own;
        //1.html解决方式
        /*content = content.replaceAll("&", "&amp;");
        content = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h5>My First Heading</h5>\n" +
                "\n" +
                "\n<h6>" + content+ "</h6>"+
                "<img src=\"http://pic33.nipic.com/20131007/13639685_123501617185_2.jpg\"  alt=\"上海鲜花港 - 郁金香\" />"+
                "</body>\n" +
                "</html>" ;*/
        content = content.replaceAll("&", "&amp;");

        String html = "<!DOCTYPE html><html>" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>title</title></head>" +
                "<body>" +
                    "<p>" +
                     content +
                    "</p>" +
                "</body>" +
                "</html>";
        //2.
        emailService.sendEMAIL("测试br上台邮件发送", content, "li.xianchun@vpclub.cn", "heyue", null);
    }


}
