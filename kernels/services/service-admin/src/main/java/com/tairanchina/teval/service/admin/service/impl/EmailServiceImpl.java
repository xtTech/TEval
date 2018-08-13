package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.Resp;
import com.sun.mail.util.MailSSLSocketFactory;
import com.tairanchina.teval.service.admin.dto.MailRequestDTO;
import com.tairanchina.teval.service.admin.service.EmailService;
import com.tairanchina.teval.service.admin.util.EmailSendUtil;
import com.tairanchina.teval.service.admin.vo.EmailMessage;
import com.tairanchina.teval.service.admin.vo.MailAuthenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * 描述:
 * 邮件发送服务实现
 *
 * @author hzds
 * @Create 2018-08 : 08 14:37
 */
@Service
public class EmailServiceImpl implements EmailService {

    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private EmailMessage message;//TODO:发送邮件相关配置暂时默认

    @Override
    public Resp<Boolean> sendEmail(MailRequestDTO requestDTO) {
        //获取系统信息
        Properties prop = new Properties();
        //添加必要的信息
        prop.put("mail.smtp.host", message.getHost());
        prop.put("mail.smtp.auth", message.isAuth());
        prop.put("mail.smtp.port", message.getPort());
        prop.put("mail.transport.protocol", message.getProtocol());
        try {
            prop.put("mail.smtp.ssl.enable",message.getSsl());
            //开启SSL加密
            if(message.getSsl()){
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                prop.put("mail.smtp.ssl.socketFactory", sf);
            }
            Authenticator auth = null;
            if(message.isAuth()){
                //设置对话和邮件服务器通讯
                auth = new MailAuthenticator(message.getFrom(), message.getAuthCode());
            }
            Session session = Session.getInstance(prop,auth);
            //控制台打印degug信息
            //session.setDebug(true);

            //构造邮件内容体
            Message sendMsg = new MimeMessage(session);
            //设置邮件主题
            sendMsg.setSubject(requestDTO.getSubject());
            //设置邮件标题
            sendMsg.setHeader("Header",requestDTO.getHeader());
            //设置发送时间
            sendMsg.setSentDate(Calendar.getInstance().getTime());
            //设置发信人地址和名字
            sendMsg.setFrom(new InternetAddress(message.getFrom(),message.getFromName()));
            //设置收件人地址
            sendMsg.setRecipient(Message.RecipientType.TO,new InternetAddress(requestDTO.getTo()));
            //设置邮件内容主题
            MimeMultipart mainPart = new MimeMultipart();

            //设置邮件内容文本部分
            MimeBodyPart bodyPart = new MimeBodyPart();
            switch (requestDTO.getContentType()) {
                case TEXT:
                    bodyPart.setText(requestDTO.getContent(),"utf-8");
                case HTML:
                    bodyPart.setContent(requestDTO.getContent(), "text/html;charset=utf-8");
                default:
                    break;
            }
            mainPart.addBodyPart(bodyPart);

            //附件部分
            List<String> attachFiles= requestDTO.getAttachFileNames();
            if(attachFiles != null && attachFiles.size() > 0){
                for (String fileName : attachFiles) {
                    bodyPart = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(fileName);
                    bodyPart.setDataHandler(new DataHandler(source));
                    bodyPart.setFileName(source.getName());
                    mainPart.addBodyPart(bodyPart);
                }
            }
            sendMsg.setContent(mainPart);
            try{
                //抄送
                if(requestDTO.getCc()){
                    sendMsg.setRecipients(Message.RecipientType.CC,EmailSendUtil.convertStrToAddress(requestDTO.getCcs()));
                }
                //密送
                if(requestDTO.getBcc()){
                    sendMsg.setRecipients(Message.RecipientType.BCC,EmailSendUtil.convertStrToAddress(requestDTO.getBccs()));
                }
            }catch (AddressException e){
                logger.info("接收邮件地址转换出错");
                e.printStackTrace();
                return Resp.error(new Resp(){{setMessage("接收邮件地址转换出错");setCode("403");setBody(false);}});
            }
            //保存以上信息
            sendMsg.saveChanges();
            //发送邮件
            Transport.send(sendMsg,sendMsg.getAllRecipients());
        }catch (GeneralSecurityException e){
            logger.info("SSL加密出错");
            e.printStackTrace();
            return Resp.error(new Resp(){{setMessage("SSL加密出错");setCode("403");setBody(false);}});
        }catch (MessagingException e){
            logger.info("构造邮件内容体出错");
            e.printStackTrace();
            return Resp.error(new Resp(){{setMessage("构造函数内容体出错");setCode("403");setBody(false);}});
        }catch (UnsupportedEncodingException e){
            logger.info("错误编码");
            e.printStackTrace();
            return Resp.error(new Resp(){{setMessage("错误编码");setCode("403");setBody(false);}});
        }
        return Resp.success(true);
    }
}
