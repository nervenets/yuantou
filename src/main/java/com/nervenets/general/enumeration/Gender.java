package com.nervenets.general.enumeration;

public enum Gender implements Enums {
    male("男"),
    female("女");

    private final String text;

    Gender(String text) {
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
