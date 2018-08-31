package com.tairanchina.teval.service.admin.util;

import com.ecfront.dew.common.Resp;
import com.sun.mail.util.MailSSLSocketFactory;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.vo.EmailMessage;
import com.tairanchina.teval.service.admin.vo.MailAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * 描述:邮件发送工具类
 *
 * @author hzds
 * @Create 2018-08 : 07 19:41
 */
public class EmailSendUtil {
    private static Logger logger = LoggerFactory.getLogger(EmailSendUtil.class);

    public static Resp<Boolean> sendEmail(MailRequestDTO requestDTO, EmailMessage message){
        //获取系统信息
        Properties prop = new Properties();
        //添加必要的信息
        prop.put("mail.smtp.host", message.getHost());
        prop.put("mail.smtp.auth", message.isAuth());
        prop.put("mail.smtp.port", message.getPort());
        prop.put("mail.transport.protocol", message.getProtocol());
        try {
            prop.put("mail.smtp.ssl.enable", message.getSsl());
            //开启SSL加密
            if (message.getSsl()) {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                prop.put("mail.smtp.ssl.socketFactory", sf);
            }
            Authenticator auth = null;
            if (message.isAuth()) {
                //设置对话和邮件服务器通讯
                auth = new MailAuthenticator(message.getFrom(), message.getAuthCode());
            }
            Session session = Session.getInstance(prop, auth);
            //控制台打印degug信息
            //session.setDebug(true);

            //构造邮件内容体
            Message sendMsg = new MimeMessage(session);
            //设置邮件主题
            sendMsg.setSubject(requestDTO.getSubject());
            //设置邮件标题
            sendMsg.setHeader("Header", requestDTO.getHeader());
            //设置发送时间
            sendMsg.setSentDate(Calendar.getInstance().getTime());
            //设置发信人地址和名字
            sendMsg.setFrom(new InternetAddress(message.getFrom(), message.getFromName()));
            //设置收件人地址
            sendMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(requestDTO.getTo()));
            //设置邮件内容主题
            MimeMultipart mainPart = new MimeMultipart();

            //设置邮件内容文本部分
            MimeBodyPart bodyPart = new MimeBodyPart();
            switch (EmailMessage.ContentType.valueOf(requestDTO.getContentType())) {
                case TEXT:
                    bodyPart.setText(requestDTO.getContent(), "utf-8");
                case HTML:
                    bodyPart.setContent(requestDTO.getContent(), "text/html;charset=utf-8");
                default:
                    break;
            }
            mainPart.addBodyPart(bodyPart);

            //附件部分
            List<String> attachFiles = requestDTO.getAttachFileNames();
            if (attachFiles != null && attachFiles.size() > 0) {
                for (String fileName : attachFiles) {
                    bodyPart = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(fileName);
                    bodyPart.setDataHandler(new DataHandler(source));
                    bodyPart.setFileName(source.getName());
                    mainPart.addBodyPart(bodyPart);
                }
            }
            sendMsg.setContent(mainPart);
            //抄送
            if (requestDTO.getCc()) {
                sendMsg.setRecipients(Message.RecipientType.CC, EmailSendUtil.convertStrToAddress(requestDTO.getCcs()));
            }
            //密送
            if (requestDTO.getBcc()) {
                sendMsg.setRecipients(Message.RecipientType.BCC, EmailSendUtil.convertStrToAddress(requestDTO.getBccs()));
            }
            //保存以上信息
            sendMsg.saveChanges();
            //发送邮件
            Transport.send(sendMsg, sendMsg.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Global.RespError.SYSTEM_ERROR.detail("邮件发送失败").get();
        }
        return Resp.success(true);
    }

    public static Address[] convertStrToAddress(String str) throws AddressException {
        if (StringUtils.isEmpty(str) || StringUtils.isEmpty(str = str.replace(" ", ""))) return null;
        String[] split = str.split(",|;");
        List<String> splitArrays = new ArrayList<>(split.length);
        for (int i = 0; i < split.length; i++) {
            if (!StringUtils.isEmpty(split[i]) && !splitArrays.contains(split[i])) {
                splitArrays.add(split[i]);
            }
        }
        Address[] addresses = split.length == 0 ? null : new Address[splitArrays.size()];
        for (int i = 0; i < splitArrays.size(); i++) {
            addresses[i] = new InternetAddress(splitArrays.get(i));
        }
        return addresses;
    }
}
