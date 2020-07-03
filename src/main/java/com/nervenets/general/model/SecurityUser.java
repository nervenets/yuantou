package com.nervenets.general.model;

import com.nervenets.general.enumeration.UnionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements Serializable {
    private long id;
    private String username;
    private String name;
    private UnionType type;
    private int expire = 7 * 24 * 60 * 60;//默认过期时间7天

    private Set<String> menus = new HashSet<>();
    private Set<String> permissions = new HashSet<>();

    public SecurityUser(long id, String username, String name, UnionType type) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.type = type;
    }
}
