package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.dao.AccountDao;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountExt;
import com.tairanchina.teval.service.admin.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 14:53
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Resp<Boolean> saveAccount(Account accountVo) {
        Account account = new Account();
        return null;
    }

    @Override
    public Resp<AccountExt> findExtByPrimaryKey(String primaryKey) {
        return Resp.success(accountDao.getExtByPrimaryKey(primaryKey));
    }
}
