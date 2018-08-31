package com.tairanchina.teval.common.service.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 22 14:22
 */
public class ListHandlerType extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,converListToString(parameter));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return converStringToList(rs.getString(columnName));
    }

    @Override
    public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return converStringToList(rs.getString(columnIndex));
    }

    @Override
    public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return converStringToList(cs.getString(columnIndex));
    }
    private String converListToString(List<String> list){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if(i==list.size()-1){
                buffer.append(list.get(i));
            }else{
                buffer.append(list.get(i)+",");
            }
        }
        return buffer.toString();
    }
    private List<String> converStringToList(String column){
        String[] array = column.split(",");
        return Arrays.asList(array);
    }
}
