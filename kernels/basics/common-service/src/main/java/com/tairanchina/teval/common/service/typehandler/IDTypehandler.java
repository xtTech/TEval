package com.tairanchina.teval.common.service.typehandler;

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
 * @Create 2018-08 : 14 16:50
 */
public class IDTypehandler extends BaseTypeHandler<Object> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        if(parameter instanceof Integer){
            ps.setInt(i,(int)parameter);
        }else if(parameter instanceof String){
            ps.setString(i,(String)parameter);
        }else{
            ps.setObject(i,parameter);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object i = rs.getObject(columnName);
        if(i instanceof Integer){
            return rs.getInt(columnName);
        }else if(i instanceof String){
            return rs.getString(columnName);
        }else{
            return i;
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object i = rs.getObject(columnIndex);
        if(i instanceof Integer){
            return rs.getInt(columnIndex);
        }else if(i instanceof String){
            return rs.getString(columnIndex);
        }else{
            return i;
        }
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object i = cs.getObject(columnIndex);
        if(i instanceof Integer){
            return cs.getInt(columnIndex);
        }else if(i instanceof String){
            return cs.getString(columnIndex);
        }else{
            return i;
        }
    }
}
