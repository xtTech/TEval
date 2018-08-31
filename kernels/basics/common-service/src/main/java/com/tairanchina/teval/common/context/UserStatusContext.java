package com.tairanchina.teval.common.context;

import com.tairanchina.teval.common.domain.core.account.Account;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 17:36
 */
public class UserStatusContext {
    private static ThreadLocal<UserStatusContext> statusContextThreadLocal = ThreadLocal.withInitial(UserStatusContext::new);

    private Account account;
    private AccountIdentity identity;

    public static UserStatusContext instance() {
        return statusContextThreadLocal.get();
    }

    public static void clear() {
        statusContextThreadLocal.remove();
        statusContextThreadLocal.set(new UserStatusContext());
    }

    public Account getAccount() {
        return account;
    }

    public UserStatusContext setAccount(Account account) {
        this.account = account;
        return this;
    }

    public AccountIdentity getIdentity() {
        return identity;
    }

    public UserStatusContext setIdentity(AccountIdentity identity) {
        this.identity = identity;
        return this;
    }
}
