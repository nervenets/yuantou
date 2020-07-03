package com.nervenets.general.adapter.jsonType;


import com.nervenets.general.adapter.HibernateJsonType;

public class StringArrayJson extends HibernateJsonType {
    public static final String NAME = "com.nervenets.general.adapter.jsonType.StringArrayJson";

    @Override
    protected Class sourceClass() {
        return String.class;
    }

    @Override
    protected boolean isArray() {
        return true;
    }
}
