package com.tairanchina.teval.common.domain.core.ext.condition;

import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.Account;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 17:46
 */
public class ExtAccountCondition extends ExtTCondition<Account> {

    private String accountFuzzyMatch;

    private Integer effectStatus;

    private List<EntityObject.Status> accountStatusList;

    public ExtAccountCondition(Account baseCondition) {
        super(baseCondition);
    }
    public ExtAccountCondition() {
        super(new Account());
    }

    public String getAccountFuzzyMatch() {
        return accountFuzzyMatch;
    }

    public ExtAccountCondition setAccountFuzzyMatch(String accountFuzzyMatch) {
        this.accountFuzzyMatch = accountFuzzyMatch;
        return this;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public ExtAccountCondition setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
        return this;
    }

    public List<EntityObject.Status> getAccountStatusList() {
        return accountStatusList;
    }

    public ExtAccountCondition setAccountStatusList(List<EntityObject.Status> accountStatusList) {
        this.accountStatusList = accountStatusList;
        return this;
    }
}
