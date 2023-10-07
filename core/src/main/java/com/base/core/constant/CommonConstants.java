package com.base.core.constant;

/**
 * 通用常量
 */
public class CommonConstants {

    /**
     * 父ID
     */
    public static final Long PARENT_ID = 0L;

    /**
     * 判断标记
     */
    public interface Flag {

        /**
         * 是
         */
        String YES = "1";

        /**
         * 否
         */
        String NO = "2";

        /**
         * 成功
         */
        String SUCCESS = "1";

        /**
         * 失败
         */
        String FAIL = "2";
    }

    /**
     * 删除状态;1：未删除；2：已删除
     */
    public interface Del {

        /**
         * 未删除
         */
        String NOT_DEL = "1";

        /**
         * 已删除
         */
        String DEL = "2";
    }

}
