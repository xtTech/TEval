package com.tairanchina.teval.common.domain.core.ext.condition;

import com.tairanchina.teval.common.domain.base.BaseObject;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 10:08
 */
public class ExtTCondition<T extends BaseObject>{

    private T baseCondition;

    public ExtTCondition(T baseCondition) {
        this.baseCondition = baseCondition;
    }

    public T getBaseCondition() {
        return baseCondition;
    }

    public ExtTCondition setBaseCondition(T baseCondition) {
        this.baseCondition = baseCondition;
        return this;
    }
}
