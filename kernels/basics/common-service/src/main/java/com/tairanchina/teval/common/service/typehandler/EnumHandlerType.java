package com.tairanchina.teval.common.service.typehandler;

import com.tairanchina.teval.common.domain.base.EntityObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 17 11:56
 */
public class EnumHandlerType extends BaseTypeHandler<EntityObject.Status> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EntityObject.Status parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public EntityObject.Status getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String i = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return EntityObject.Status.valueOf(i);
        }

    }

    @Override
    public EntityObject.Status getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String i = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return EntityObject.Status.valueOf(i);
        }
    }

    @Override
    public EntityObject.Status getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String i = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return EntityObject.Status.valueOf(i);
        }
    }
}
