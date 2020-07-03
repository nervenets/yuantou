package com.nervenets.general.enumeration;


import io.swagger.annotations.ApiModel;

@ApiModel("资产变动状态")
public enum FlowStatus implements Enums {
    locking("锁定中"),
    handling("处理中"),
    verify("验证中"),
    success("成功"),
    failed("失败");

    private final String text;

    FlowStatus(String text) {
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
