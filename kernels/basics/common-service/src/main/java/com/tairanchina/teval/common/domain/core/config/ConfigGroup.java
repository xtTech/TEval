package com.tairanchina.teval.common.domain.core.config;

import com.tairanchina.teval.common.domain.base.CycleEntityObject;

/**
 * 说明: 配置组
 * NOTE: 组装配置树
 * Note: 修改配置树保留记录
 * NOTE: createdBy、updatedBy暂定String, __system__
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/14.
 */
public class ConfigGroup extends CycleEntityObject<String, String> {
    /**
     * 配置组名称
     */
    private String name;
    /**
     * 配置组描述
     */
    private String description;
    /**
     * 父配置组
     */
    private Long parentGroupId;
    /**
     * 流程控制
     * js: function($configGroup, callback) {
     *      try {
     *          var username = $configGroup.CHANNEL_USERNAME // 渠道账号
     *          var password = $configGroup.CHANNEL_PASSWORD // 渠道密码
     *          if(username && password) {
     *              callback(null, true) // 进行下一步配置
     *          } else {
     *              callback(null, false) // 停止
     *          }
     *      } catch(e) {
     *          callback(e) // 出错，提示e.message
     *      }
     * }
     */
    private String flowControl;
    /**
     * 配置组创建者所属大类
     * Tip: 判断配置项创建者，如果配置项写死在代码中，说明是系统配置；如果配置项在界面上创建，分析它的所属关系
     * e.g. TEval大部分配置都是系统创建: SYSTEM
     * e.g. APP_ID、APP_SECRET配置是微信登录服务创建的: SERVICE
     * e.g. 企业用户升级DIY字段由企业用户所属的应用创建: APP
     */
    private String creatorClass;
    /**
     * 配置组创建者
     * e.g. TEval大部分配置都是系统创建: __system__
     * e.g. APP_ID、APP_SECRET配置是微信登录服务创建的: wechat_service_uuid
     * e.g. 企业用户升级DIY字段由企业用户所属的应用创建: xxx_app_uuid
     */
    private String creatorId;
    /**
     * 配置组配置者所属大类
     */
    private String configurerClass;

    public String getName() {
        return name;
    }

    public ConfigGroup setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ConfigGroup setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getParentGroupId() {
        return parentGroupId;
    }

    public ConfigGroup setParentGroupId(Long parentGroupId) {
        this.parentGroupId = parentGroupId;
        return this;
    }

    public String getFlowControl() {
        return flowControl;
    }

    public ConfigGroup setFlowControl(String flowControl) {
        this.flowControl = flowControl;
        return this;
    }

    public String getCreatorClass() {
        return creatorClass;
    }

    public ConfigGroup setCreatorClass(String creatorClass) {
        this.creatorClass = creatorClass;
        return this;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public ConfigGroup setCreatorId(String creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getConfigurerClass() {
        return configurerClass;
    }

    public ConfigGroup setConfigurerClass(String configurerClass) {
        this.configurerClass = configurerClass;
        return this;
    }
}
