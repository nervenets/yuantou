package com.nervenets.general.enumeration;

public enum PayType implements Enums {
    other("其他"),
    cash("现金"),
    surplus("余额支付"),
    wxpay("微信支付"),
    alipay("支付宝支付"),
    unionpay("银联支付"),
    pos("POS刷卡"),
    groupBuy("团购"),
    wxpay_online("微信在线支付"),
    alipay_online("支付宝在线支付");

    private final String text;

    PayType(String text) {
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
