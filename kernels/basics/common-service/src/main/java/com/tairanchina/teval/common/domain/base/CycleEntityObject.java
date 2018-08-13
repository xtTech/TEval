package com.tairanchina.teval.common.domain.base;

import java.util.Date;

/**
 * 说明: 有生命周期的实体对象
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class CycleEntityObject<T, Z> extends EntityObject<T, Z> {
    /**
     * 出生时间
     */
    private Date fromDate;
    /**
     * 死亡时间
     */
    private Date thruDate;

    public Date getFromDate() {
        return fromDate;
    }

    public CycleEntityObject setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public CycleEntityObject setThruDate(Date thruDate) {
        this.thruDate = thruDate;
        return this;
    }
}
