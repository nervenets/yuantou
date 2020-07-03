package com.nervenets.general.enumeration;

import io.swagger.annotations.ApiModel;

@ApiModel("资产类型")
public enum WealthType implements Enums {
    money("现金"),
    integral("积分"),
    ;

    private final String text;

    WealthType(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
