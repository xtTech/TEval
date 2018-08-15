package com.tairanchina.teval.common.domain.base;

import java.util.Date;

/**
 * 说明: 关系对象
 * NOTE: 多用于多对多关系
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/13.
 */
public class RelationObject<FROM extends EntityObject, TO extends EntityObject, Z> extends BaseObject<Long, Z> {
    /**
     * 产生关系的一方
     */
    private FROM fromEntity;
    /**
     * 产生关系的另一方
     */
    private TO toEntity;
    /**
     * 关系类型
     */
    private String relationType;
    /**
     * 关系开始时间
     */
    private Date fromDate;
    /**
     * 关系结束时间
     */
    private Date thruDate;
    /**
     * 新关系ID
     */
    private Long newRelationId;
    /**
     * 备注: Plain Text or JSON format
     */
    private String remark;

    public FROM getFromEntity() {
        return fromEntity;
    }

    public RelationObject setFromEntity(FROM fromEntity) {
        this.fromEntity = fromEntity;
        return this;
    }

    public TO getToEntity() {
        return toEntity;
    }

    public RelationObject setToEntity(TO toEntity) {
        this.toEntity = toEntity;
        return this;
    }

    public String getRelationType() {
        return relationType;
    }

    public RelationObject setRelationType(String relationType) {
        this.relationType = relationType;
        return this;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public RelationObject setFromDate(Date fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public RelationObject setThruDate(Date thruDate) {
        this.thruDate = thruDate;
        return this;
    }

    public Long getNewRelationId() {
        return newRelationId;
    }

    public RelationObject setNewRelationId(Long newRelationId) {
        this.newRelationId = newRelationId;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public RelationObject setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
