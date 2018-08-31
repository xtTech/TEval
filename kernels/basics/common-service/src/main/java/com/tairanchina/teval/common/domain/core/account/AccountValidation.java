package com.tairanchina.teval.common.domain.core.account;

import com.tairanchina.teval.common.domain.base.CycleEntityObject;

/**
 * 说明: 账号验证
 * 验证码的存储、是否验证过、验证码有效期校验、验证错误次数是否达上限
 * Tip: 所属AccountIdentity By updatedBy
 * Tip: 验证码有效期 By Cycle Attributes
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class AccountValidation extends CycleEntityObject<Long, String> {
    /**
     * 验证码
     */
    private String code;
    /**
     * 是否验证过
     */
    private Boolean isValidated;
    /**
     * 错误次数
     */
    private Integer errorTimes;

    public String getCode() {
        return code;
    }

    public AccountValidation setCode(String code) {
        this.code = code;
        return this;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public AccountValidation setValidated(Boolean validated) {
        isValidated = validated;
        return this;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public AccountValidation setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
        return this;
    }
}
