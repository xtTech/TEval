package com.tairanchina.teval.service.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 描述:
 * 发送邮件参数
 *
 * @author hzds
 * @Create 2018-08 : 08 14:02
 */
@ApiModel(description = "邮件发送接收参数")
public class MailRequestDTO {

    @ApiModelProperty(name = "收件人邮箱",required = true)
    //@NotBlank(message = "收件人邮箱不能为空")
    private  String to;

    @ApiModelProperty(name = "邮件头")
    private  String header;

    @ApiModelProperty(name = "邮件标题")
    private  String subject;

    @ApiModelProperty(name = "是否抄送",notes = "默认false")
    private Boolean cc = false;

    @ApiModelProperty(name = "抄送人",notes = "抄送人邮箱（多个抄送人用‘,’或‘;’隔开）")
    private String ccs;

    @ApiModelProperty(name = "是否密送",notes = "默认false")
    private Boolean bcc = false;

    @ApiModelProperty(name = "密送人",notes = "密送人邮箱（多个密送人用‘,’或‘;’隔开）")
    private String bccs;

    @ApiModelProperty(name = "内容格式",notes = "必须为TEXT|HTML")
    //@Pattern(regexp = "^TEXT|HTML$",message = "邮件内容格式参数错误")
    //@NotBlank(message = "邮件内容格式不能为空")
    private ContentType contentType;

    @ApiModelProperty(name = "邮件内容",notes = "")
    private String content = "你好！恭喜你获得平安免费参保体验！";

    @ApiModelProperty(name = "邮件附件",notes = "上传附件为本地文件地址数组")
    private List<String> attachFileNames;

    public String getTo() {
        return to;
    }

    public MailRequestDTO setTo(String to) {
        this.to = to;
        return this;
    }

    public String getHeader() {
        return header;
    }

    public MailRequestDTO setHeader(String header) {
        this.header = header;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailRequestDTO setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Boolean getCc() {
        return cc;
    }

    public MailRequestDTO setCc(Boolean cc) {
        this.cc = cc;
        return this;
    }

    public String getCcs() {
        return ccs;
    }

    public MailRequestDTO setCcs(String ccs) {
        this.ccs = ccs;
        return this;
    }

    public Boolean getBcc() {
        return bcc;
    }

    public MailRequestDTO setBcc(Boolean bcc) {
        this.bcc = bcc;
        return this;
    }

    public String getBccs() {
        return bccs;
    }

    public MailRequestDTO setBccs(String bccs) {
        this.bccs = bccs;
        return this;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public MailRequestDTO setContentType(String contentType) {
        this.contentType = ContentType.valueOf(contentType);
        return this;
    }

    public String getContent() {
        return content;
    }

    public MailRequestDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public List<String> getAttachFileNames() {
        return attachFileNames;
    }

    public MailRequestDTO setAttachFileNames(List<String> attachFileNames) {
        this.attachFileNames = attachFileNames;
        return this;
    }
    //邮件类型枚举
    public enum ContentType{
        TEXT,HTML
    }
}
