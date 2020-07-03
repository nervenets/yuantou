package com.nervenets.general.enumeration;

public enum Platform implements Enums {
    none("无"),
    h5("H5"),
    android("安卓"),
    ios("苹果"),
    pc("PC");

    private final String text;

    Platform(String text) {
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
