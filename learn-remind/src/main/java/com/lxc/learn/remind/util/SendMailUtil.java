package com.lxc.learn.remind.util;

import com.lxc.learn.remind.enums.EmailSystemConfigEnum;
import com.lxc.learn.remind.model.entity.RemindEmailTemplate;
import com.lxc.learn.remind.service.EmailTemplateService;
import com.lxc.learn.remind.service.SendEmailRecordService;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

/**
 * JavaMail发送邮件:前提是QQ邮箱里帐号设置要开启POP3/SMTP协议
 */
@Slf4j
public class SendMailUtil {

    private static Session session;

    static {
        try{
            Properties prop = new Properties();
            // 开启debug调试，以便在控制台查看
            prop.setProperty("mail.debug", "true");
            // 设置邮件服务器主机名
            prop.setProperty("mail.host", "smtp.qq.com");
            // 发送服务器需要身份验证
            prop.setProperty("mail.smtp.auth", "true");
            // 发送邮件协议名称
            prop.setProperty("mail.transport.protocol", "smtp");

            // 开启SSL加密，否则会失败
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            // 创建session
            session = Session.getInstance(prop);
            // 通过session得到transport对象
            Transport ts = session.getTransport();
        }catch (Exception e){
            log.error("初始化session : {}",e);
        }
    }

    /*public static void main(String[] args) throws Exception {

        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect("smtp.qq.com", "2410308914", "nhbgwxyzokjvdidj");//后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）
        // 创建邮件
        Message message = createSimpleMail(session);
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }*/


    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session,String from, String to) throws Exception{
        //1.创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
        //2.设置发件人地址
        msg.setFrom(new InternetAddress(from));
        /**
         * 3.设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
        //4.设置邮件主题
        msg.setSubject("邮件主题(包含图片和附件)","UTF-8");

        //下面是设置邮件正文
        //msg.setContent("简单的纯文本邮件！", "text/html;charset=UTF-8");

        // 5. 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh = new DataHandler(new FileDataSource("src\\mailTestPic.png"));
        // 将图片数据添加到"节点"
        image.setDataHandler(dh);
        // 为"节点"设置一个唯一编号（在文本"节点"将引用该ID）
        image.setContentID("mailTestPic");

        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent("这是一张图片<br/><a href='http://www.cnblogs.com/ysocean/p/7666061.html'><img src='cid:mailTestPic'/></a>", "text/html;charset=UTF-8");

        // 7. （文本+图片）设置 文本 和 图片"节点"的关系（将 文本 和 图片"节点"合成一个混合"节点"）
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        mm_text_image.setSubType("related");    // 关联关系

        // 8. 将 文本+图片 的混合"节点"封装成一个普通"节点"
        // 最终添加到邮件的 Content 是由多个 BodyPart 组成的 Multipart, 所以我们需要的是 BodyPart,
        // 上面的 mailTestPic 并非 BodyPart, 所有要把 mm_text_image 封装成一个 BodyPart
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        // 9. 创建附件"节点"
        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
        DataHandler dh2 = new DataHandler(new FileDataSource("src\\mailTestDoc.docx"));
        // 将附件数据添加到"节点"
        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        // 11. 设置整个邮件的关系（将最终的混合"节点"作为邮件的内容添加到邮件对象）
        msg.setContent(mm);
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());

        return msg;
    }


    public static boolean sendEmail(EmailSystemConfigEnum emailEnum, String to, String templateCode,
                                 Object ... list){
        boolean send = ApplicationContextUtil.getBean(SendEmailRecordService.class).exist(emailEnum.getEmail(),to,templateCode);
        if (send){
            log.info("已发送：from:{}，to:{}，templateCode:{}",emailEnum.getEmail(),to,templateCode);
            return false;
        }

        RemindEmailTemplate template = ApplicationContextUtil.getBean(EmailTemplateService.class).getRandomEmailTemplate(templateCode);
        if (template == null){
            return false;
        }
        Transport ts = null;
        try {
            ts = session.getTransport();
            // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
            ts.connect("smtp.qq.com", emailEnum.getId(), emailEnum.getPassword());//后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）
            // 创建邮件对象
            MimeMessage message = new MimeMessage(session);
            // 指明邮件的发件人
            message.setFrom(new InternetAddress(emailEnum.getEmail()));
            // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 邮件的标题
            message.setSubject(template.getfEmailSubject());
            // 邮件的文本内容
            String content = template.getfEmailTemplateContent();
            if (list != null || list.length !=0){
                content = MessageFormat.format(content,list);
            }
            message.setContent(content, "text/html;charset=UTF-8");
            // 返回创建好的邮件对象
            // 发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ApplicationContextUtil.getBean(SendEmailRecordService.class).saveRecord(emailEnum.getEmail(),
                    to,templateCode);
        }catch (Exception e){
            log.info("发送：{}",e);
            return false;
        }finally {
            if (ts != null){
                try{
                    ts.close();
                }catch (Exception var){
                    log.error("{}",var);
                }
            }
        }
        return true;
    }


}
