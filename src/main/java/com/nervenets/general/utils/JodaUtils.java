/*
 * Copyright (c) 2007 IJO Technologies Ltd.
 * www.ijotechnologies.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * IJO Technologies ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with IJO Technologies.
 */
package com.nervenets.general.utils;


import org.joda.time.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public final class JodaUtils {

    public static int getTimestamp() {
        long l = new Date().getTime();
        return (int) (l / 1000);
    }

    public static int getTimestamp(DateTime dateTime) {
        final long time = dateTime.toDate().getTime();
        return (int) (time / 1000);
    }

    public static Date parseTimestamp(int timestamp) {
        long r = (long) timestamp * 1000L;
        return new Date(r);
    }

    public static String timeLongToString() {
        return timeLongToString("yyyy-MM-dd HH:mm:ss");
    }

    public static String timeLongToString(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    public static String timeLongToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static String timeLongToString(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time));
    }

    public static DateTime now() {
        return new DateTime();
    }

    public static LocalDate today() {
        return new LocalDate();
    }

    public static DateMidnight parseDateMidnight(LocalDate date) {
        if (date != null) {
            return new DateMidnight(date.toString("yyyy-MM-dd"));
        }
        return null;
    }

    public static LocalDate parse(String dateAsText, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = simpleDateFormat.parse(dateAsText);
            return new LocalDate(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static boolean isToday(int time) {
        final DateTime dateTime = parse(time * 1000L);
        final DateTime today = new DateTime();
        return dateTime.getYear() == today.getYear() && dateTime.getMonthOfYear() == today.getMonthOfYear() && dateTime.getDayOfMonth() == today.getDayOfMonth();
    }

    public static Integer hoursBetween(int olderTime, int newerTime) {
        return Hours.hoursBetween(parse(olderTime * 1000L), parse(newerTime * 1000L)).getHours();
    }

    public static Integer hoursBetween(LocalDate older, LocalDate newer) {
        return Hours.hoursBetween(older, newer).getHours();
    }

    public static Integer daysBetween(int olderTime, int newerTime) {
        return Days.daysBetween(parse(olderTime * 1000L), parse(newerTime * 1000L)).getDays();
    }

    public static Integer daysBetween(LocalDate creationDate, LocalDate expireDate) {
        return Days.daysBetween(creationDate, expireDate).getDays();
    }

    public static Date parse(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = localDate.toString("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * @param dateTime
     * @return Tue, May 26,2009
     */
    public static String getDateInfoFromDateTime(DateTime dateTime) {
        DateTime.Property property = dateTime.dayOfWeek();
        String weekday = property.getAsShortText();
        return weekday + dateTime.toString(", MMM dd, yyyy");
    }

    public static String getTimeInfoFromDateTime(DateTime dateTime) {
        return dateTime.toString("HH:mm a");
    }

    public static int getPeriodAsDay(DateTime dateTime1, DateTime dateTime2) {
        Period period = new Period(dateTime1, dateTime2, PeriodType.days());
        return period.getDays();
    }

    public static int getPeriodAsSecond(DateTime dateTime1, DateTime dateTime2) {
        Period period = new Period(dateTime1, dateTime2, PeriodType.seconds());
        return period.getSeconds();
    }

    public static DateTime parse(long timemillis) {
        return new DateTime(timemillis);
    }

    public static int getStartOfThisWeek(int week) {
        Calendar c = Calendar.getInstance();
        final int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        if (week != weekOfYear) {
            c.add(Calendar.WEEK_OF_YEAR, week - weekOfYear);
        }
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfThisWeek(int week) {
        Calendar c = Calendar.getInstance();
        final int weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        if (week != weekOfYear) {
            c.add(Calendar.WEEK_OF_YEAR, week - weekOfYear);
        }
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfLastWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfLastWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 7);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfThisMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfThisMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.add(Calendar.MONTH, -1);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.add(Calendar.MONTH, -1);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfToday() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfToday() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfYesterday() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfYesterday() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfThisHour() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfThisHour() {
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getStartOfLastHour() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static int getEndOfLastHour() {
        final Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return (int) (c.getTime().getTime() / 1000);
    }

    public static String formatTime(int seconds) {
        StringBuilder result = new StringBuilder();
        int hours = seconds / 3600;
        if (hours > 0) result.append(hours).append("小时");
        int min = seconds % 3600 / 60;
        if (min > 0) result.append(min).append("分");
        int sec = seconds % 60;
        result.append(sec).append("秒");
        return result.toString();
    }
}
