package com.nervenets.general.entity;

import com.nervenets.general.utils.JodaUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsMessage implements Serializable {
    private int time = JodaUtils.getTimestamp();
    private String action;
    private Object data;

    public WsMessage(String action) {
        this.action = action;
    }

    public WsMessage(String action, Object data) {
        this.action = action;
        this.data = data;
    }
}
