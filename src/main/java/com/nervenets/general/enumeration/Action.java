package com.nervenets.general.enumeration;

public enum Action implements Enums {
    add("新增"),
    edit("编辑"),
    delete("删除");

    private final String text;

    Action(String text) {

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
