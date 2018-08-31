package com.tairanchina.teval.service.admin.dto;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 15:20
 */
public class LoginResponseDTO {
    /**
     * 账户ID
     */
    private String accountId;
    /**
     *用户姓名
     */
    private String name;
    /**
     *用户头像
     */
    private String avatar;
    /**
     *用户身份信息
     */
    private String account_secret;
    /**
     *用户登录描述
     */
    private String description;
    /**
     *用户登录状态
     */
    private String status;
    /**
     *用户备注
     */
    private String remark;
    /**
     *用户登录Token
     */
    private String token;
    /**
     *用户凭证Id
     */
    private Long identityId;

    public Long getIdentityId() {
        return identityId;
    }

    public LoginResponseDTO setIdentityId(Long identityId) {
        this.identityId = identityId;
        return this;
    }

    public String getName() {
        return name;
    }

    public LoginResponseDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public LoginResponseDTO setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getAccount_secret() {
        return account_secret;
    }

    public LoginResponseDTO setAccount_secret(String account_secret) {
        this.account_secret = account_secret;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LoginResponseDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public LoginResponseDTO setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public LoginResponseDTO setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getToken() {
        return token;
    }

    public LoginResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public LoginResponseDTO setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }
}
