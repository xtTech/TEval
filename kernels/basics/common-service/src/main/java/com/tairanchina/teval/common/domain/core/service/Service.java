package com.tairanchina.teval.common.domain.core.service;

import com.tairanchina.teval.common.domain.base.EntityObject;

import java.util.List;

/**
 * 说明: 服务
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/14.
 */
public class Service extends EntityObject<String, String> {
    /**
     * 服务名称
     */
    private String name;
    /**
     * 服务描述
     */
    private String description;
    /**
     * 服务图标
     */
    private String icon;
    /**
     * 服务版本
     */
    private Integer version;
    /**
     * 服务类型
     */
    private String type;
    /**
     * 厂商/提供方
     */
    private String vendor;
    /**
     * 配置组集合
     */
    private List<String> configGroupIds;

    public String getName() {
        return name;
    }

    public Service setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Service setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Service setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public Service setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getType() {
        return type;
    }

    public Service setType(String type) {
        this.type = type;
        return this;
    }

    public String getVendor() {
        return vendor;
    }

    public Service setVendor(String vendor) {
        this.vendor = vendor;
        return this;
    }

    public List<String> getConfigGroupIds() {
        return configGroupIds;
    }

    public Service setConfigGroupIds(List<String> configGroupIds) {
        this.configGroupIds = configGroupIds;
        return this;
    }
}
