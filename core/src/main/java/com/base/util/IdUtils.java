package com.base.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtils {
    /**
     * 20位末尾的数字id
     */
    private static volatile int Guid = 100;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成ID
     * @param prefix
     * @return
     */
    public static String generateId(String prefix) {
        return prefix + generateSeq();
    }
    private static String generateSeq() {
        return getCurrentTime() + getMachineNo() + getRandomNum();
    }

    private static String getCurrentTime() {
        return LocalDateTime.now().format(timeFormatter);
    }

    private static String getRandomNum() {
        return RandomUtils.generate(6);
    }

    private static String getMachineNo() {
        int ipAddress;
        try {
            ipAddress = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipAddress = 0;
        }

        String formatStr = Integer.toHexString(ipAddress);
        StringBuilder buf = new StringBuilder("00000000");
        buf.replace(8 - formatStr.length(), 8, formatStr);

        return formatStr;
    }

    private final static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * 生成唯一数字id 长度二十
     *
     * @return
     */
    public static String getGuid() {

        IdUtils.Guid += 1;

        //获取4位年份数字
        long now = System.currentTimeMillis();
        //获取时间戳
        String time = dateFormat.format(now);
        String info = now + "";
        //获取三位随机数
        //int ran=(int) ((Math.random()*9+1)*100);
        //要是一段时间内的数据连过大会有重复的情况，所以做以下修改
        int ran = 0;
        if (IdUtils.Guid > 999) {
            IdUtils.Guid = 100;
        }
        ran = IdUtils.Guid;

        return time + info.substring(2, info.length()) + ran;
    }
}
