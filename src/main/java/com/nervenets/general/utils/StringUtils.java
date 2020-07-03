package com.nervenets.general.utils;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.trim;

/**
 * Created by Joe
 * Date: 2011-5-2 22:04:45
 */
public final class StringUtils {
    public static String toUTF8(String value) {
        try {
            byte[] bytes = value.getBytes("ISO-8859-1");
            return new String(bytes, "utf8");
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        }
        return value;
    }

    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static String chinaToUnicode(String str) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            int chr1 = (char) str.charAt(i);
            if (chr1 >= 19968 && chr1 <= 171941) {//汉字范围 \u4e00-\u9fa5 (中文)
                result += "\\u" + Integer.toHexString(chr1);
            } else {
                result += str.charAt(i);
            }
        }
        return result;
    }

    public static String filterEmoji(String source) {
        if (null == source) return null;
        if (!isBlank(source)) {
            return source.replaceAll("[^\\u0000-\\uFFFF]", "").trim();
        } else {
            return source.trim();
        }
        //return source;
    }

    public static boolean hasAnyEmpty(String... strings) {
        for (String string : strings) {
            if (org.springframework.util.StringUtils.isEmpty(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("\\w+@(\\w+\\.){1,3}\\w+");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isRedisNull(String string) {
        return org.springframework.util.StringUtils.isEmpty(string)
                || "NIL".equals(string.toUpperCase())
                || "NULL".equals(string.toUpperCase());
    }

    public static boolean isEmpty(String string) {
        return org.springframework.util.StringUtils.isEmpty(string);
    }

    public static String toString(Object obj) {
        return null == obj ? "" : String.valueOf(obj);
    }

    public static String randomString(int length) {
        String chars = "ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678";
        /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
        int maxPos = chars.length();
        String result = "";
        for (int i = 0; i < length; i++) {
            result += chars.charAt(NumberUtil.randomNumber(0, maxPos - 1));
        }
        return result;
    }

    public static String zeroBeginFillString(int number, int length) {
        final String numberStr = number + "";
        if (numberStr.length() >= length) return numberStr;
        StringBuilder result = new StringBuilder();
        for (int i = numberStr.length(); i < length; i++) {
            result.append("0");
        }
        result.append(numberStr);
        return result.toString();
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String readRequestBody(HttpServletRequest request) {
        if (request.getContentLength() > 0) {
            byte[] body = new byte[request.getContentLength()];
            try {
                IOUtils.readFully(request.getInputStream(), body);
            } catch (IOException e) {
                return "请求内容读取错误：" + e.getMessage();
            }
            return new String(body);
        } else
            return null;
    }

    public static String clearHtmlTags(String string) {
        String regEx_html = "<[^>]+>";

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(string);
        string = m_html.replaceAll(""); //过滤html标签

        return trim(string); //返回文本字符串
    }
}
