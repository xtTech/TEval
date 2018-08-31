package com.tairanchina.teval.service.admin.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix="email.qq")
public class EmailMessage
{
  /**
   * 邮件配置相关
   */
  //服务器（QQ:smtp.qq.com;163:smtp.163.com）
  private  String host;

  //端口号（必须是465）
  private String port;

  //是否需要认证
  private Boolean auth;

  //协议
  private String protocol;

  //发件方邮箱
  private  String from;

  //发送方邮箱授权码
  private String authCode;

  //发件方昵称
  private  String fromName;

  private Boolean ssl;

  /**
   * 邮件信息部分
   */

  //收件人邮箱
  private  String to;

  //邮件头
  private  String header;

  //邮件标题
  private  String subject;

  //是否抄送
  private Boolean cc;
  //抄送人
  private String ccs;

  //是否密送
  private Boolean bcc;
  //密送人
  private String bccs;

  //邮件的类型
  private ContentType contentType;

  //邮件的内容
  private String content = "邮件内容";

  // 邮件附件的文件名
  private List<String> attachFileNames;

  public List<String> getAttachFileNames() {
    return attachFileNames;
  }

  public String getHost() {
    return host;
  }

  public EmailMessage setHost(String host) {
    this.host = host;
    return this;
  }

  public String getPort() {
    return port;
  }

  public EmailMessage setPort(String port) {
    this.port = port;
    return this;
  }

  public boolean isAuth() {
    return auth;
  }

  public EmailMessage setAuth(boolean auth) {
    this.auth = auth;
    return this;
  }

  public String getProtocol() {
    return protocol;
  }

  public EmailMessage setProtocol(String protocol) {
    this.protocol = protocol;
    return this;
  }

  public String getFrom() {
    return from;
  }

  public EmailMessage setFrom(String from) {
    this.from = from;
    return this;
  }

  public String getAuthCode() {
    return authCode;
  }

  public EmailMessage setAuthCode(String authCode) {
    this.authCode = authCode;
    return this;
  }

  public String getFromName() {
    return fromName;
  }

  public EmailMessage setFromName(String fromName) {
    this.fromName = fromName;
    return this;
  }


  public String getTo() {
    return to;
  }

  public EmailMessage setTo(String to) {
    this.to = to;
    return this;
  }

  public String getHeader() {
    return header;
  }

  public EmailMessage setHeader(String header) {
    this.header = header;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public EmailMessage setSubject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getCcs() {
    return ccs;
  }

  public EmailMessage setCcs(String ccs) {
    this.ccs = ccs;
    return this;
  }

  public String getBccs() {
    return bccs;
  }

  public EmailMessage setBccs(String bccs) {
    this.bccs = bccs;
    return this;
  }


  public ContentType getContentType() {
    return contentType;
  }

  public EmailMessage setContentType(ContentType contentType) {
    this.contentType = contentType;
    return this;
  }

  public String getContent() {
    return content;
  }

  public EmailMessage setContent(String content) {
    this.content = content;
    return this;
  }

  public EmailMessage setAttachFileNames(List<String> attachFileNames) {
    this.attachFileNames = attachFileNames;
    return this;
  }

  public Boolean getAuth() {
    return auth;
  }

  public EmailMessage setAuth(Boolean auth) {
    this.auth = auth;
    return this;
  }

  public Boolean getSsl() {
    return ssl;
  }

  public EmailMessage setSsl(Boolean ssl) {
    this.ssl = ssl;
    return this;
  }

  public Boolean getCc() {
    return cc;
  }

  public EmailMessage setCc(Boolean cc) {
    this.cc = cc;
    return this;
  }

  public Boolean getBcc() {
    return bcc;
  }

  public EmailMessage setBcc(Boolean bcc) {
    this.bcc = bcc;
    return this;
  }

  //邮件类型枚举
  public enum ContentType{
    TEXT,HTML
  }
}