package com.nervenets.general.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Create by: Dinjone
 * Date: 2009-11-14 - 10:20:53
 */
public abstract class LocaleResolver {
    public static Locale resolveLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        locale = useDefaultLocaleIfNecessary(locale);
        return locale;
    }

    protected static Locale useDefaultLocaleIfNecessary(Locale locale) {
        if (locale == null) {
            locale = new Locale("en");
        }
        return locale;
    }
}
