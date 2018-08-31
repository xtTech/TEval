package com.tairanchina.teval.service.admin.vo;

import java.util.Date;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 23 9:43
 */
public class AccountIdentityVo {

    private String email;
    private String password;
    private String passwordEncryptSalt;
    private Date fromDate;
    private Date thruDate;
    private String createdBy;
    private String updatedBy;
    private String status;
    private String remark;

    public String getEmail() {
        return email;
    }

    public AccountIdentityVo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountIdentityVo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordEncryptSalt() {
        return passwordEncryptSalt;
    }

    public AccountIdentityVo setPasswordEncryptSalt(String passwordEncryptSalt) {
        this.passwordEncryptSalt = passwordEncryptSalt;
        return this;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public AccountIdentityVo setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public AccountIdentityVo setThruDate(Date thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AccountIdentityVo setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AccountIdentityVo setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public AccountIdentityVo setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public AccountIdentityVo setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
