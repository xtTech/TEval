package com.tairanchina.teval.common.domain.core;

import com.tairanchina.teval.common.domain.base.CycleEntityObject;

/**
 * 说明: 账号凭证
 * Tip: 所属Account By updatedBy
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class AccountIdentity extends CycleEntityObject<Long, String> {
    /**
     * 邮箱
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 密码加密盐
     */
    private String passwordEncryptSalt;

    public String getEmail() {
        return email;
    }

    public AccountIdentity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AccountIdentity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordEncryptSalt() {
        return passwordEncryptSalt;
    }

    public AccountIdentity setPasswordEncryptSalt(String passwordEncryptSalt) {
        this.passwordEncryptSalt = passwordEncryptSalt;
        return this;
    }
}