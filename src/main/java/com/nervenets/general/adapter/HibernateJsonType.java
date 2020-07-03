package com.nervenets.general.adapter;

import com.alibaba.fastjson.JSON;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.TextType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Joe on 2018/1/26.
 */
public abstract class HibernateJsonType implements UserType, Serializable {

    @Override
    public int[] sqlTypes() {
        return new int[]{getSqlType()};
    }

    @Override
    public Class returnedClass() {
        if (isArray()) return List.class;
        return sourceClass();
    }

    protected abstract Class sourceClass();

    protected abstract boolean isArray();

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1 || o != null && o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] strings, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        String json = resultSet.getString(strings[0]);
        if (json == null || json.trim().length() == 0) {
            return null;
        }
        if (isArray()) {
            return JSON.parseArray(json, sourceClass());
        }
        return JSON.parseObject(json, sourceClass());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            preparedStatement.setNull(i, getSqlType());
        } else {
            preparedStatement.setString(i, JSON.toJSONString(value));
        }
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /**
     * 本类型实例是否可变
     *
     * @return 是否可变
     */
    @Override
    public boolean isMutable() {
        return true;
    }

    /* 序列化 */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return ((Serializable) value);
    }

    /* 反序列化 */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    public int getSqlType() {
        return TextType.INSTANCE.sqlType();
    }
}
