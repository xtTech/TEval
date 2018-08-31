package com.tairanchina.teval.service.admin.dto;

import java.util.Date;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 27 11:00
 */
public class ValidateTokenResponseDTO {
    /**
     * 登陆凭证Id
     */
    private Integer identId;
    /**
     * 用户ID
     */
    private String accountId;
    /**
     * 生效时间
     */
    private Date IssuedAt;
    /**
     * token失效时刻
     */
    private Date expiresAt;

    public Integer getIdentId() {
        return identId;
    }

    public ValidateTokenResponseDTO setIdentId(Integer identId) {
        this.identId = identId;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public ValidateTokenResponseDTO setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public ValidateTokenResponseDTO setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public Date getIssuedAt() {
        return IssuedAt;
    }

    public ValidateTokenResponseDTO setIssuedAt(Date issuedAt) {
        IssuedAt = issuedAt;
        return this;
    }
}
