package com.tairanchina.teval.common.domain.core.ext.entity;

import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 17:43
 */
public class AccountExt extends Account {

    private AccountIdentity identity;

    public AccountIdentity getIdentity() {
        return identity;
    }

    public AccountExt setIdentity(AccountIdentity identity) {
        this.identity = identity;
        return this;
    }
}
