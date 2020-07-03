package com.nervenets.general.enumeration;

import io.swagger.annotations.ApiModel;

@ApiModel("关联类型")
public enum UnionType implements Enums {
    normal("无"),
    user("用户"),
    customer("商户、门店用户"),
    store("门店"),
    flows("流水"),
    order("订单"),
    table("台桌"),
    product("商品"),
    account("系统账号"),
    ;

    private final String text;

    UnionType(String text) {
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
