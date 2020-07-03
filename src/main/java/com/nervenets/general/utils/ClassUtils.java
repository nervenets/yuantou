package com.nervenets.general.utils;

import java.lang.reflect.Field;

public final class ClassUtils {
    public static Field getDeclaredField(Class<?> source, String[] fields) throws NoSuchFieldException {
        Field field = null;
        Class<?> s = source;
        for (String f : fields) {
            field = getDeclaredField(s, f);
            s = field.getType();
        }
        return field;
    }

    public static Field getDeclaredField(Class<?> source, String field) throws NoSuchFieldException {
        return source.getDeclaredField(field);
    }
}
