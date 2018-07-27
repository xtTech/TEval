package com.tairanchina.teval.common.domain;


import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class TimestampEntity implements Serializable {

    @ApiModelProperty("创建时间")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    protected Date createTime;

    @ApiModelProperty("更新时间")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    protected Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
