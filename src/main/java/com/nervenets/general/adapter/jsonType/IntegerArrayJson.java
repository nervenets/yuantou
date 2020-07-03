package com.nervenets.general.adapter.jsonType;

import com.nervenets.general.adapter.HibernateJsonType;

public class IntegerArrayJson extends HibernateJsonType {
    public static final String NAME = "com.nervenets.general.adapter.jsonType.IntegerArrayJson";

    @Override
    protected Class sourceClass() {
        return Integer.class;
    }

    @Override
    protected boolean isArray() {
        return true;
    }
}
