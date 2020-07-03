package com.nervenets.general.adapter.jsonType;


import com.nervenets.general.adapter.HibernateJsonType;
import com.nervenets.general.entity.NNLog;

public class NNLogArrayJson extends HibernateJsonType {
    public static final String NAME = "com.nervenets.general.adapter.jsonType.NNLogArrayJson";

    @Override
    protected Class sourceClass() {
        return NNLog.class;
    }

    @Override
    protected boolean isArray() {
        return true;
    }
}
