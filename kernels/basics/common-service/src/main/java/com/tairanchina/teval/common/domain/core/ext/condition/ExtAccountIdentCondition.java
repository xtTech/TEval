package com.tairanchina.teval.common.domain.core.ext.condition;

import com.tairanchina.teval.common.domain.base.EntityObject;
import com.tairanchina.teval.common.domain.core.account.AccountIdentity;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 10:12
 */
public class ExtAccountIdentCondition extends ExtTCondition<AccountIdentity> {

    private String identFuzzyMatch;

    private Integer effectStatus;

    private List<EntityObject.Status> identStatusList;

    public ExtAccountIdentCondition() {
        super(new AccountIdentity());
    }

    public ExtAccountIdentCondition(AccountIdentity baseCondition) {
        super(baseCondition);
    }

    public String getIdentFuzzyMatch() {
        return identFuzzyMatch;
    }

    public ExtAccountIdentCondition setIdentFuzzyMatch(String identFuzzyMatch) {
        this.identFuzzyMatch = identFuzzyMatch;
        return this;
    }

    public Integer getEffectStatus() {
        return effectStatus;
    }

    public ExtAccountIdentCondition setEffectStatus(Integer effectStatus) {
        this.effectStatus = effectStatus;
        return this;
    }

    public List<EntityObject.Status> getIdentStatusList() {
        return identStatusList;
    }

    public ExtAccountIdentCondition setIdentStatusList(List<EntityObject.Status> identStatusList) {
        this.identStatusList = identStatusList;
        return this;
    }
}
