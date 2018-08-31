package com.tairanchina.teval.service.admin.dto;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 17:55
 */
public class AccountTokenRequestDTO {
    private String email;
    private String encPassword;
    private String accountId;
    private Long identId;
    /**
     * token过期时间(单位:分钟)
     */
    private Long expMinutes;

    public String getEmail() {
        return email;
    }

    public AccountTokenRequestDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getExpMinutes() {
        return expMinutes;
    }

    public AccountTokenRequestDTO setExpMinutes(Long expMinutes) {
        this.expMinutes = expMinutes;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountTokenRequestDTO setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Long getIdentId() {
        return identId;
    }

    public AccountTokenRequestDTO setIdentId(Long identId) {
        this.identId = identId;
        return this;
    }

    public String getEncPassword() {
        return encPassword;
    }

    public AccountTokenRequestDTO setEncPassword(String encPassword) {
        this.encPassword = encPassword;
        return this;
    }
}
