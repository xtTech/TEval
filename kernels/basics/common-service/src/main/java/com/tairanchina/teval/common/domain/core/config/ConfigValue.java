package com.tairanchina.teval.common.domain.core.config;

import com.tairanchina.teval.common.domain.base.CycleEntityObject;

/**
 * 说明: 配置值
 * Note: 修改配置值保留记录
 * Tip: 配置者 By updatedBy
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/14.
 */
public class ConfigValue extends CycleEntityObject<Long, String> {
    /**
     * 配置项ID
     */
    private String configId;
    /**
     * 配置项值
     */
    private String value;

    public String getConfigId() {
        return configId;
    }

    public ConfigValue setConfigId(String configId) {
        this.configId = configId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public ConfigValue setValue(String value) {
        this.value = value;
        return this;
    }
}
