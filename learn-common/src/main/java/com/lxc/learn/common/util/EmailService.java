package com.lxc.learn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.*;

@Slf4j
@Service
public class EmailService {

    @Value("${email.account}")
    private String account;
    @Value("${email.password}")
    private String password;
    @Value("${email.port}")
    public String port;
    @Value("${email.host}")
    public String SMTPHost;
    @Value("${email.nameSendBy}")
    public String nameSendBy;
    @Value("${email.auth}")
    public Boolean auth;





    /**
     * 邮件发送
     *
     * @param subject    主题
     * @param content    正文
     * @param sendTo     收件地址
     * @param nameSendTo 收件人名称,可以不填写
     * @throws Exception
     */
    public void sendEMAIL(String subject, String content, String sendTo, String nameSendTo
            , URLDataSource fileDataSrc, String fileName,String filePath) throws Exception {

        fileName = javax.mail.internet.MimeUtility.encodeText(fileName);
        log.debug("file name {}", fileName);
        // 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", SMTPHost);
        if (!StringUtils.isEmpty(port)){
            props.put("mail.smtp.port",port);
        }
        Session session = null;
        if (auth) {
            props.setProperty("mail.smtp.auth", "true");
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(account, password);
                }
            });
        } else {
            props.setProperty("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props);
        }

        if (log.isDebugEnabled()) {
            session.setDebug(true);// 设置为debug模式, 可以查看详细的发送 log
        }

        // 创建一封邮件
        MimeMessage message;
        if (null != fileDataSrc) {
            message = createMimeMessage(session, account, sendTo, nameSendBy, nameSendTo
                    , subject, content, fileDataSrc, fileName,filePath);
        } else {
            message = createMimeMessage(session, account, sendTo, nameSendBy, nameSendTo
                    , subject, content, null, null,filePath);
        }

        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail
            , String nameSendBy, String nameSendTo
            , String subject, String content
            , URLDataSource fileDataSource, String fileName,String filePath) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, nameSendBy, "UTF-8"));
        String[] receiveMails = receiveMail.split(";");
        InternetAddress[] sendTo = new InternetAddress[receiveMails.length];
        for (int i= 0;i<receiveMails.length;i++){
            sendTo[i] = new InternetAddress(receiveMails[i]);
        }
        //  可以增加多个收件人、抄送、密送
        message.setRecipients(MimeMessage.RecipientType.TO, sendTo);
        //  邮件主题
        message.setSubject(subject, "UTF-8");

        if (null == fileDataSource || null == fileName) {
            //带附件的邮件
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);

            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fileDataSource));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
        }else if (!StringUtils.isEmpty(filePath)){
            //创建附件节点  读取本地文件,并读取附件名称
            MimeBodyPart file = new MimeBodyPart();
            DataHandler dataHandler = new DataHandler(new FileDataSource(filePath));
            file.setDataHandler(dataHandler);
            file.setFileName(javax.mail.internet.MimeUtility.encodeText(dataHandler.getName()));
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(content,"text/html;charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(file);
            multipart.addBodyPart(text);
            message.setContent(multipart);
        }else {
            message.setContent(content, "text/html;charset=UTF-8");
        }
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
    /**
     * @param subject
     * @param content
     * @param sendTo
     * @param fileName 附件文件名
     * @param url      附件地址 URL 对象
     * @throws Exception
     */
    public void sendEMAIL(String subject, String content, String sendTo
            , String fileName, URL url) throws Exception {
        log.info("发送邮件： " + sendTo);
        DataSource fileDataSrc = null;
        if (null == fileName || null == url) {
            fileDataSrc = new URLDataSource(url);
        }
        sendEMAIL(subject, content, sendTo, null
                , fileDataSrc, fileName);
    }
    /**
     * 邮件发送
     *
     * @param subject    主题
     * @param content    正文
     * @param sendTo     收件地址
     * @param nameSendTo 收件人名称,可以不填写
     * @throws Exception
     */
    public void sendEMAIL(String subject, String content, String sendTo, String nameSendTo
            , DataSource fileDataSrc, String fileName) throws Exception {

        fileName = javax.mail.internet.MimeUtility.encodeText(fileName);
        log.debug("file name {}", fileName);
        // 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", SMTPHost);
        if (!StringUtils.isEmpty(port)){
            props.put("mail.smtp.port",port);
        }
        Session session = null;
        if (auth) {
            props.setProperty("mail.smtp.auth", "true");
            session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(account, password);
                }
            });
        } else {
            props.setProperty("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props);
        }

        if (log.isDebugEnabled()) {
            session.setDebug(true);// 设置为debug模式, 可以查看详细的发送 log
        }

        // 创建一封邮件
        MimeMessage message;
        if (null != fileDataSrc) {
            message = createMimeMessage(session, account, sendTo, nameSendBy, nameSendTo
                    , subject, content, fileDataSrc, fileName);
        } else {
            message = createMimeMessage(session, account, sendTo, nameSendBy, nameSendTo
                    , subject, content, null, null);
        }

        // 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session     和服务器交互的会话
     * @param sendMail    发件人邮箱
     * @param receiveMail 收件人邮箱
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail
            , String nameSendBy, String nameSendTo
            , String subject, String content
            , DataSource fileDataSource, String fileName) throws Exception {

        Address[] a = new Address[]{};
        List<Address> list = new ArrayList<>();
        List<String> result = Arrays.asList(receiveMail.split(";"));
        result.forEach(s -> {
            try {
                list.add(new InternetAddress(s.trim(), s.trim()) );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, nameSendBy, "UTF-8"));
        //  可以增加多个收件人、抄送、密送
        //message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, receiveMail));
        message.setRecipients(MimeMessage.RecipientType.TO,list.toArray(a));
        //  邮件主题
        message.setSubject(subject, "UTF-8");

        if (null == fileDataSource || null == fileName) {
            //带附件的邮件
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fileDataSource));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
        } else {
            message.setContent(content, "text/html;charset=UTF-8");
        }
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }
}
