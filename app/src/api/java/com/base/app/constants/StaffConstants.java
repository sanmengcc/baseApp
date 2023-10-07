package com.base.app.constants;

/**
 * 员工相关常量
 */
public class StaffConstants {

    /**
     * 管理员类型
     */
    public interface AdminType{

        /**
         * 超级管理员
         */
        String SUPER_ADMIN = "000";

        /**
         * 系统管理员
         */
        String ADMIN = "001";

        /**
         * 普通管理
         */
        String STAFF = "002";
    }

    /**
     * 工作状态
     */
    public interface WorkStatus{

        /**
         * 在职
         */
        String WORK = "1";

        /**
         * 离职
         */
        String LEAVE = "2";
    }
}
