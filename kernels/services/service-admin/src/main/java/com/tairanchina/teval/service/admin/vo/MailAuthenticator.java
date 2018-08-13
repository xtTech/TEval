package com.tairanchina.teval.service.admin.vo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 描述:
 * 邮箱验证
 *
 * @author hzds
 * @Create 2018-08 : 06 14:20
 */
public class MailAuthenticator extends Authenticator {
    private String username;
    private String password;

    public MailAuthenticator(){
        super();
    }
    public MailAuthenticator(String userName , String password) {
        super();
        this.username = userName;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(this.username,this.password);
    }
}
