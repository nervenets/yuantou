package com.nervenets.general.enumeration;


import com.nervenets.general.utils.JodaUtils;

public enum Time implements Enums {
    anytime("随时"),
    hour("小时"),
    day("天"),
    week("周"),
    month("月"),
    year("年");

    private final String text;

    Time(String text) {
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

    public int nowStartTime() {
        switch (this) {
            case anytime:
                break;
            case hour:
                return JodaUtils.getStartOfThisHour();
            case day:
                return JodaUtils.getStartOfToday();
            case week:
                return JodaUtils.getStartOfThisWeek();
            case month:
                return JodaUtils.getStartOfThisMonth();
        }
        return 0;
    }
}
