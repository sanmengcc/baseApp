package com.base.util;

/**
 * 字符串工具
 */
public class StringUtils {

    /**
     * 字符串补0操作
     * @param str
     * @param length
     * @return
     */
    public static String appendZero(String str, Integer length) {
        return String.format("%0" + length + "d", Integer.valueOf(str));
    }
}
