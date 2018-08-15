package com.tairanchina.teval.common.domain.core.account;

import com.tairanchina.teval.common.domain.base.EntityObject;

/**
 * 说明: 账号
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class Account extends EntityObject<String, String> {
    /**
     * 账号身份证
     */
    private String accountSecret;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 账号头像
     */
    private String avatar;
    /**
     * 账号描述
     */
    private String description;
    /**
     * 账号类型
     */
    private String type;
    /**
     * 域名: xxx.teval.cn
     */
    private String domain;

    public String getAccountSecret() {
        return accountSecret;
    }

    public Account setAccountSecret(String accountSecret) {
        this.accountSecret = accountSecret;
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Account setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Account setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getType() {
        return type;
    }

    public Account setType(String type) {
        this.type = type;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Account setDomain(String domain) {
        this.domain = domain;
        return this;
    }
}
