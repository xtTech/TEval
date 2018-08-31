package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.domain.core.account.AccountValidation;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 11:54
 */
public interface AccountValidationService {
    /**
     * 保存验证记录
     * @param validation
     * @return
     */
    Resp<AccountValidation> saveAccountValidation(AccountValidation validation);

}
