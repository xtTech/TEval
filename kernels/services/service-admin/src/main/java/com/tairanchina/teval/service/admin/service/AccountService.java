package com.tairanchina.teval.service.admin.service;

import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountExt;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 16 20:02
 */
public interface AccountService {
    /**
     * 保存账户基本信息
     * @param account
     * @return
     */
    Resp<Boolean> saveAccount(Account account);

    /**
     * 根据主键查找账户及凭证信息
     * @param PrimaryKey
     * @return
     */
    Resp<AccountExt> findExtByPrimaryKey(String PrimaryKey);
}
