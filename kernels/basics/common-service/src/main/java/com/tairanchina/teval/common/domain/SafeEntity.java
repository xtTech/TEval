package com.tairanchina.teval.common.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class SafeEntity extends TimestampEntity {

    @ApiModelProperty("创建人编码")
    @Column(nullable = false)
    protected String createUser;

    @ApiModelProperty("更新人编码")
    @Column(nullable = false)
    protected String updateUser;

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @PreUpdate
    public void onUpdate() {
        // TODO
        setUpdateUser("");
    }

    @PrePersist
    public void onCreate() {
        // TODO
        setCreateUser("");
        setUpdateUser("");
    }

}
