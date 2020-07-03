package com.nervenets.general.utils;

import org.apache.commons.lang.math.JVMRandom;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by shengshibotong.com. User: Joe by 13-3-21 上午9:45
 */
public final class NumberUtil {

    public static boolean isNaN(double d) {
        return Math.abs(d) < 0.00001;
    }

    public static boolean isNaN(String d) {
        return "NaN".equals(d);
    }

    public static long randomNumber(long min, long max) {
        return new JVMRandom().nextLong((int) (max - min + 1)) + min;
    }

    public static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max + 1) % (max - min + 1) + min;
    }

    public static BigDecimal randBigDecimal(BigDecimal min, BigDecimal max) {
        if (max.equals(min)) return min;
        return new BigDecimal(Math.random() * (max.doubleValue() - min.doubleValue()) + min.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String decimalFormat(double number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    public static String formatDistance(double distance) {
        if (distance < 10 && distance - 1.0 > 0.001) {
            return Double.toString(distance).substring(0, 3) + "km";
        } else if (distance >= 10) {
            return (int) distance + "km";
        } else {
            String result = Integer.toString((int) (distance * 1000));
            return (result.length() > 3 ? result.substring(0, 3) : result) + "m";
        }
    }

    /**
     * allNumber 总数，chooseNumber 随机数目，返回随机数的数组
     */
    public static int[] randomNumArr(int allNumber, int chooseNumber) {
        if (allNumber >= chooseNumber) {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < allNumber; i++) {
                list.add(i);
            }
            Collections.shuffle(list);
            list = list.subList(0, chooseNumber);
            int[] ins = new int[chooseNumber];
            for (int i = 0; i < chooseNumber; i++) {
                ins[i] = list.get(i);
            }
            return ins;
        }
        return null;
    }

    public static long ipToLong(String ipAddress) {
        long result = 0;
        try {
            String[] ipAddressInArray = ipAddress.split("\\.");
            for (int i = 3; i >= 0; i--) {
                long ip = Long.parseLong(ipAddressInArray[3 - i]);
                result |= ip << (i * 8);
            }
        } catch (Exception e) {
            //
        }
        return result;
    }

    public static String longToIp(long ip) {
        return ((ip >> 24) & 0xFF) + "."
                + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "."
                + (ip & 0xFF);
    }

    public static double fen2Yuan(long fen) {
        return BigDecimal.valueOf(0.01).multiply(BigDecimal.valueOf(fen)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /*public static double li2Yuan(long li) {
        return BigDecimal.valueOf(0.001).multiply(BigDecimal.valueOf(li)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }*/

    public static double addDouble(double v1, double v2) {
        return BigDecimal.valueOf(v1).add(BigDecimal.valueOf(v2)).doubleValue();
    }

    public static double subDouble(double v1, double v2) {
        return BigDecimal.valueOf(v1).subtract(BigDecimal.valueOf(v2)).doubleValue();
    }

    public static double mulDouble(double v1, double v2) {
        return BigDecimal.valueOf(v1).multiply(BigDecimal.valueOf(v2)).doubleValue();
    }

    public static double divDouble(double v1, double v2) {
        return BigDecimal.valueOf(v1).divide(BigDecimal.valueOf(v2)).doubleValue();
    }
}
