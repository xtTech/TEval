package com.tairanchina.teval.common.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class StatusEntity implements Serializable {

    @ApiModelProperty("是否禁用")
    @Column(nullable = false)
    protected Boolean disabled = true;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
