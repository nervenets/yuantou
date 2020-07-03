package com.nervenets.general.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisCacheableObject extends KeyValue {
    /**
     * 是否为数组
     */
    private boolean array = false;

    public RedisCacheableObject(String key, Object value, boolean array) {
        super(key, value);
        this.array = array;
    }
}
