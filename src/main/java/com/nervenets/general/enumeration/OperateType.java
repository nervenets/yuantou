package com.nervenets.general.enumeration;

import io.swagger.annotations.ApiModel;

@ApiModel("资产变动类型")
public enum OperateType implements Enums {
    recharge("充值"),
    withdraw("提现"),
    consume("消费"),
    refund("退款"),
    ;
    private final String text;

    OperateType(String text) {
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
