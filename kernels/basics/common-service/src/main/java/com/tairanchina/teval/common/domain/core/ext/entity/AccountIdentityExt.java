package com.tairanchina.teval.common.domain.core.ext.entity;

import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 9:43
 */
public class AccountIdentityExt extends AccountIdentity {
    /**
     * 凭证所属账户
     */
    private Account account;

    public Account getAccount() {
        return account;
    }

    public AccountIdentityExt setAccount(Account account) {
        this.account = account;
        return this;
    }
}
