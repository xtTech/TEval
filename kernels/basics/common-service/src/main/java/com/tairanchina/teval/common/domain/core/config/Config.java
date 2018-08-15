package com.tairanchina.teval.common.domain.core.config;

import com.tairanchina.teval.common.domain.base.EntityObject;

import java.util.Date;
import java.util.List;

/**
 * 说明: 配置项
 * NOTE: createdBy、updatedBy暂定String, __system__
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/14.
 */
public class Config extends EntityObject<String, String> {
    /**
     * 配置项标识
     */
    private String key;
    /**
     * 配置项名称
     */
    private String name;
    /**
     * 配置项描述
     */
    private String description;
    /**
     * 配置项值类型
     */
    private Type valueType;
    /**
     * 配置项是否选填
     */
    private Boolean optional;
    /**
     * 配置项默认值
     */
    private String defaultValue;
    /**
     * 配置项可选值列表
     * e.g. [{"label":"上海","value":"shanghai"},...]
     */
    private String options;
    /**
     * 配置项值校验
     * e.g. [{"type":"regexp",expression:"^\\d{6,}$","message":"请填写数字字符组成的且长度不小于6的信息"},...]
     */
    private String constraints;
    /**
     * 配置项所属分组
     */
    private Long groupId;
    /**
     * 配置项组内排序
     */
    private Integer sort;
    /**
     * 配置项显示载体，即图形化控件
     */
    private String carrier;
    /**
     * 配置项创建者所属大类
     * Tip: 判断配置项创建者，如果配置项写死在代码中，说明是系统配置；如果配置项在界面上创建，分析它的所属关系
     * e.g. TEval大部分配置都是系统创建: SYSTEM
     * e.g. APP_ID、APP_SECRET配置是微信登录服务创建的: SERVICE
     * e.g. 企业用户升级DIY字段由企业用户所属的应用创建: APP
     */
    private String creatorClass;
    /**
     * 配置项创建者
     * e.g. TEval大部分配置都是系统创建: __system__
     * e.g. APP_ID、APP_SECRET配置是微信登录服务创建的: wechat_service_uuid
     * e.g. 企业用户升级DIY字段由企业用户所属的应用创建: xxx_app_uuid
     */
    private String creatorId;
    /**
     * 配置项配置者所属大类
     */
    private String configurerClass;

    /**
     * 配置项值类型枚举
     * @class TypeConverter
     */
    public enum Type {
        STRING(String.class),
        INTEGER(Integer.class),
        LONG(Long.class),
        DOUBLE(Double.class),
        BOOLEAN(Boolean.class),
        DATE(Date.class),
        LIST_STRING(List.class, String.class),
        LIST_INTEGER(List.class, Integer.class),
        LIST_LONG(List.class, Long.class),
        LIST_DOUBLE(List.class, Double.class),
        LIST_BOOLEAN(List.class, Boolean.class),
        LIST_DATE(List.class, Date.class),
        URL(String.class);

        private Class clazz;
        private Class clazz1;

        Type(Class clazz) {
            this.clazz = clazz;
        }

        Type(Class clazz, Class clazz1) {
            this(clazz);
            this.clazz1 = clazz1;
        }

        public Class getClazz() {
            return clazz;
        }

        public Type setClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public Class getClazz1() {
            return clazz1;
        }

        public Type setClazz1(Class clazz1) {
            this.clazz1 = clazz1;
            return this;
        }
    }

    public String getKey() {
        return key;
    }

    public Config setKey(String key) {
        this.key = key;
        return this;
    }

    public String getName() {
        return name;
    }

    public Config setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Config setDescription(String description) {
        this.description = description;
        return this;
    }

    public Type getValueType() {
        return valueType;
    }

    public Config setValueType(Type valueType) {
        this.valueType = valueType;
        return this;
    }

    public Boolean getOptional() {
        return optional;
    }

    public Config setOptional(Boolean optional) {
        this.optional = optional;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Config setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getOptions() {
        return options;
    }

    public Config setOptions(String options) {
        this.options = options;
        return this;
    }

    public String getConstraints() {
        return constraints;
    }

    public Config setConstraints(String constraints) {
        this.constraints = constraints;
        return this;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Config setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public Config setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getCarrier() {
        return carrier;
    }

    public Config setCarrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public String getCreatorClass() {
        return creatorClass;
    }

    public Config setCreatorClass(String creatorClass) {
        this.creatorClass = creatorClass;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public Config setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getConfigurerClass() {
        return configurerClass;
    }

    public Config setConfigurerClass(String configurerClass) {
        this.configurerClass = configurerClass;
        return this;
    }
}
