package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.dao.AccountValidationDao;
import com.tairanchina.teval.common.domain.core.account.AccountValidation;
import com.tairanchina.teval.service.admin.service.AccountValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 11:56
 */
@Service
public class AccountValidationServiceImpl implements AccountValidationService {

    @Autowired
    private AccountValidationDao validationDao;

    @Override
    public Resp<AccountValidation> saveAccountValidation(AccountValidation validation) {
        return null;
    }
}
