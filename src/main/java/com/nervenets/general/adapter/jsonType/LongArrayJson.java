package com.nervenets.general.adapter.jsonType;

import com.nervenets.general.adapter.HibernateJsonType;

public class LongArrayJson extends HibernateJsonType {
    public static final String NAME = "com.nervenets.general.adapter.jsonType.LongArrayJson";

    @Override
    protected Class sourceClass() {
        return Long.class;
    }

    @Override
    protected boolean isArray() {
        return true;
    }
}
