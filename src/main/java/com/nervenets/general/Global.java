package com.nervenets.general;


import org.apache.commons.lang.ArrayUtils;

/**
 * Created by joe on 17-3-16.
 */
@SuppressWarnings("ALL")
public final class Global {
    public static final String[] defaultIgnoreProperties(String... mores) {
        final String[] bases = {"id", "version", "deleted", "createTime"};
        return (String[]) ArrayUtils.addAll(bases, mores);
    }

    public static final class RegExp {
        public static final String MOBILE = "^0{0,1}(1[0-9][0-9])[0-9]{8}$";
        public static final String EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    }

    public static final class Constants {
        public static final String REDIS_USER_WEALTH_LOCK = "wealth(%s)";
        public static final String MOBILE_VALIDATION_CODE = "mvc(%s)";
        public static final String SERVICE_TIME_COUNT_KEY = "stck(%s-%s)";
        public static final String SESSION_ID = "$SESSION_ID$";
        public static final String SESSION_USER = "$SESSION_USER$";
        public static final String SESSION_ENFORCE = "$SESSION_ENFORCE$";
        public static final String TOKEN_KEY = "User-Token";
        public static final String PLATFORM_KEY = "User-Platform";
    }
}
