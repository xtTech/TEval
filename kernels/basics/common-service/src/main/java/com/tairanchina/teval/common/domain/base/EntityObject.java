package com.tairanchina.teval.common.domain.base;

/**
 * 说明: 实体对象
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class EntityObject<T, Z> extends BaseObject<T, Z> {
    /**
     * 实体状态
     */
    private Status status;
    /**
     * 备注: Plain Text or JSON format
     */
    private String remark;

    public Status getStatus() {
        return status;
    }

    public EntityObject setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public EntityObject setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public enum Status{
        INACTIVE,
        ACTIVE,
        INVALID
    }
}
