package com.tairanchina.teval.service.admin.dto;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 23 10:28
 */
public class SendMailResponseDTO {
    /**
     * 邮件发送有效
     */
    private String sendEmail;
    /**
     * 返回提示信息
     */
    private String message;
    /**
     * 激活邮件生效时间（分钟）
     */
    private Long effectiveMinutes;

    public String getSendEmail() {
        return sendEmail;
    }

    public SendMailResponseDTO setSendEmail(String sendEmail) {
        this.sendEmail = sendEmail;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SendMailResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public Long getEffectiveMinutes() {
        return effectiveMinutes;
    }

    public SendMailResponseDTO setEffectiveMinutes(Long effectiveMinutes) {
        this.effectiveMinutes = effectiveMinutes;
        return this;
    }
}
