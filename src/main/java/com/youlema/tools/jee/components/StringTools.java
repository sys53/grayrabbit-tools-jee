/**
 * Yolema.com Inc.
 * Copyright (c) 2011-2013 All Rights Reserved.
 */
package com.youlema.tools.jee.components;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.youlema.tools.jee.exceptions.JEEToolsException;

/**
 * 字符串工具类
 * 
 * @author liyd
 * @version $Id: StringTools.java, v 0.1 2013-1-22 下午2:53:05 liyd Exp $
 */
public final class StringTools {

    private static final String PREFIX = "\\u";

    private StringTools() {
    }

    /**
     * 转换中文字符集编码
     * 
     * @param str
     * @param origCode 源编码
     * @param destCode 目标编码
     * @return
     */
    public static String encode(String str, String origCode, String destCode) {

        String result = null;
        try {
            if (!StringUtils.isBlank(str)) {
                byte[] bytes = str.getBytes(origCode);
                result = new String(bytes, destCode);
            }
        } catch (UnsupportedEncodingException e) {
            throw new JEEToolsException(e);
        }
        return result;
    }

    /**
     * 在字符串中替换html标记所用的六个特殊字符：& \ " ' &lt; &gt;
     * 
     * @param str
     * @return
     */
    public static String htmlEncode(String str) {
        String sTemp = str;
        sTemp = StringUtils.replace(sTemp, "&", "&amp;");
        sTemp = StringUtils.replace(sTemp, "\"", "&quot;");
        sTemp = StringUtils.replace(sTemp, "'", "&#039;");
        sTemp = StringUtils.replace(sTemp, "<", "&lt;");
        sTemp = StringUtils.replace(sTemp, ">", "&gt;");
        return sTemp;
    }

    /**
     * 将字符串转换为ascii编码
     * 
     * @param str
     * @return
     */
    public static String native2Ascii(String str) {
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            sb.append(char2Ascii(chars[i]));
        }
        return sb.toString();
    }

    /**
     * 将字符转换为ascii编码
     * 
     * @param c
     * @return
     */
    private static String char2Ascii(char c) {
        if (c > 255) {
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX);
            int code = (c >> 8);
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            code = (c & 0xFF);
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
            return sb.toString();
        } else {
            return Character.toString(c);
        }
    }

    /**
     * 将asc编码转换为字符集
     * 
     * @param str
     * @return
     */
    public static String ascii2Native(String str) {
        StringBuilder sb = new StringBuilder();
        int begin = 0;
        int index = str.indexOf(PREFIX);
        while (index != -1) {
            sb.append(str.substring(begin, index));
            sb.append(ascii2Char(str.substring(index, index + 6)));
            begin = index + 6;
            index = str.indexOf(PREFIX, begin);
        }
        sb.append(str.substring(begin));
        return sb.toString();
    }

    /**
     * 替换指定位置的字符 只有位置正确且字符匹配时才会替换
     * 
     * @param text 操作的字符串
     * @param origStr 原字符
     * @param destStr 替换的字符
     * @param index 字符所在位置,以第一个字符位置为准,从0开始
     * @return
     */
    public static String replaceIndexStr(String text, String origStr, String destStr, int index) {

        StringBuilder sb = new StringBuilder();

        String before = StringUtils.substring(text, 0, index);

        String after = StringUtils.substring(text, index);

        if (StringUtils.startsWith(after, origStr)) {
            sb.append(before);
            sb.append(destStr);
            sb.append(StringUtils.substring(after, origStr.length()));
            return sb.toString();
        }
        return text;
    }

    /**
     * 将ascii编码转换为字符集
     * 
     * @param str
     * @return
     */
    private static char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException(
                "Ascii string of a native character must be 6 character.");
        }
        if (!PREFIX.equals(str.substring(0, 2))) {
            throw new IllegalArgumentException(
                "Ascii string of a native character must start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

}
