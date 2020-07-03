package com.nervenets.general.enumeration;

public enum ProductType implements Enums {
    normal("普通标准商品"),
    count("按实际量(斤)计数商品"),
    time("计时"),
    packet("打包商品"),
    ;

    private final String text;

    ProductType(String text) {
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
