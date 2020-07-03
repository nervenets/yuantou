package com.nervenets.general.entity;

import com.alibaba.fastjson.JSONObject;
import com.nervenets.general.enumeration.UnionType;
import com.nervenets.general.utils.JodaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MQMessage implements Serializable {
    private int type;
    private int time = JodaUtils.getTimestamp();
    private UnionType unionType;
    private long unionId;
    private JSONObject data = new JSONObject();

    public MQMessage(int type, UnionType unionType, long unionId, long ip, KeyValue... keyValues) {
        this.type = type;
        this.unionType = unionType;
        this.unionId = unionId;
        this.addData("ip", ip);
        for (KeyValue kv : keyValues) {
            this.addData(kv.getKey(), kv.getValue());
        }
    }

    public void addData(String key, Object value) {
        this.data.put(key, value);
    }

    public int getIntValue(String key) {
        return this.data.containsKey(key) ? this.data.getIntValue(key) : 0;
    }

    public long getLongValue(String key) {
        return this.data.containsKey(key) ? this.data.getLongValue(key) : 0L;
    }

    public String getStringValue(String key) {
        return this.data.getString(key);
    }
}
