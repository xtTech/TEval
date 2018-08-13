package com.tairanchina.teval.common.domain.base;

import java.util.Date;

/**
 * 说明: 基础对象
 * T 为唯一ID的类型
 *      (Number: auto_increment id or snowflake generated number)
 *      (String: 32 length uuid)
 * Z 为创建者或更新者的ID类型
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public abstract class BaseObject<T, Z> {
    /**
     * 唯一ID
     */
    private T id;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 创建者
     */
    private Z createdBy;
    /**
     * 更新者
     */
    private Z updatedBy;

    public T getId() {
        return id;
    }

    public BaseObject setId(T id) {
        this.id = id;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public BaseObject setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public BaseObject setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
        return this;
    }

    public Z getCreatedBy() {
        return createdBy;
    }

    public BaseObject setCreatedBy(Z createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Z getUpdatedBy() {
        return updatedBy;
    }

    public BaseObject setUpdatedBy(Z updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }
}
