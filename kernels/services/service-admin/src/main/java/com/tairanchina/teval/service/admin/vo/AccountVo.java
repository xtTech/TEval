package com.tairanchina.teval.service.admin.vo;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 14:47
 */
public class AccountVo {
    /**
     * 账号身份证
     */
    private String accountSecret;
    /**
     * 账号名称
     */
    private String name;
    /**
     * 账号头像
     */
    private String avatar;
    /**
     * 账号描述
     */
    private String description;
    /**
     * 账号类型
     */
    private String type;
    /**
     * 域名: xxx.teval.cn
     */
    private String domain;

    /**
     * 备注: Plain Text or JSON format
     */
    private String remark;

    private String createdBy;

    private String updatedBy;
}
