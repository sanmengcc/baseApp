package com.base.util;

/**
 * 字符串工具
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{

    /**
     * 星号
     */
    private static final String START = "*";

    /**
     * 字符串补0操作
     * @param str
     * @param length
     * @return
     */
    public static String appendZero(String str, Integer length) {
        return String.format("%0" + length + "d", Integer.valueOf(str));
    }

    /**
     * 查找指定字符串是否匹配
     *
     * @param str     指定字符串
     * @param pattern 需要检查的字符串
     * @return 是否匹配
     */
    public static boolean matches(String str, String pattern) {
        if (ValidateHelper.isEmptyString(pattern) || ValidateHelper.isEmptyString(str)) {
            return false;
        }

        // 替换空格
        pattern = pattern.replaceAll("\\s*", "");
        // pattern截取开始位置
        int beginOffset = 0;
        // 前星号的偏移位置
        int formerStarOffset = -1;
        // 后星号的偏移位置
        int latterStarOffset = -1;

        String remainingUrl = str;
        String prefixPattern = "";
        String suffixPattern = "";

        boolean result = false;
        do {
            formerStarOffset = indexOf(pattern, START, beginOffset);
            prefixPattern = substring(pattern, beginOffset, formerStarOffset > -1 ? formerStarOffset : pattern.length());

            // 匹配前缀Pattern
            result = remainingUrl.contains(prefixPattern);
            // 已经没有星号，直接返回
            if (formerStarOffset == -1) {
                return result;
            }

            // 匹配失败，直接返回
            if (!result) {
                return false;
            }

            if (!ValidateHelper.isEmptyString(prefixPattern)) {
                remainingUrl = substringAfter(str, prefixPattern);
            }

            // 匹配后缀Pattern
            latterStarOffset = indexOf(pattern, START, formerStarOffset + 1);
            suffixPattern = substring(pattern, formerStarOffset + 1, latterStarOffset > -1 ? latterStarOffset : pattern.length());

            result = remainingUrl.contains(suffixPattern);
            // 匹配失败，直接返回
            if (!result) {
                return false;
            }

            if (ValidateHelper.isNotEmptyString(suffixPattern)) {
                remainingUrl = substringAfter(str, suffixPattern);
            }

            // 移动指针
            beginOffset = latterStarOffset + 1;

        }
        while (ValidateHelper.isNotEmptyString(suffixPattern) && ValidateHelper.isNotEmptyString(remainingUrl));

        return true;
    }
}
