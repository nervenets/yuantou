package com.nervenets.general.i18n;


import com.nervenets.general.utils.PropertiesLoader;
import com.nervenets.general.utils.StringUtils;

/**
 * Create by: Dinjone
 * Date: 2009-11-14 - 10:20:19
 */
public class Translator {
    private static PropertiesLoader messageLoader = new PropertiesLoader("messages.properties");

    public Translator() {
    }

    public static String translate(Enum enumType) {
        String enumCode = resolveEnumCode(enumType);
        return doTranslate(enumCode);
    }

    public static String translate(String code) {
        String property = messageLoader.getProperty(code);
        return StringUtils.isEmpty(property) ? code : property;
    }

    public static String translate(Boolean bool) {
        String boolCode = resolveBooleanCode(bool);
        return doTranslate(boolCode);
    }

    private static String doTranslate(String code) {
        String property = messageLoader.getProperty(code);
        return StringUtils.isEmpty(property) ? code : property;
    }

    private static String resolveBooleanCode(Boolean bool) {
        String packName = "java.lang.";
        return packName + bool.toString();
    }

    private static String resolveEnumCode(Enum enumType) {
        String clazzName = enumType.getClass().getName();
        String packName = enumType.getClass().getPackage().getName();
        clazzName = clazzName.replace(packName, "enum").toLowerCase();
        return clazzName + "." + enumType.toString().toLowerCase();
    }
}
