package com.tairanchina.teval.common.dao;

import com.tairanchina.teval.common.domain.base.BaseObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 17 15:20
 */
@Repository
public interface BaseDao<T extends BaseObject> {

    T getByPrimaryKey(@Param("pk") Object primaryKey);

    List<T> findByCondition(@Param("_") T instance);

    int insert(T instance);

    int updateByPrimaryKey(@Param("pk") Object primaryKey, @Param("_") T instance);

    int deleteByPrimaryKey(@Param("pk") Object primaryKey);
}
