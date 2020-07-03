package com.nervenets.general.hibernate.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * MYSQL方言
 *
 * @author Administrator
 */
public class UTF8MySQL5InnoDBDialect extends MySQL5InnoDBDialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci";
    }
}
