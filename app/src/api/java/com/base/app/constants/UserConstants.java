package com.base.app.constants;

/**
 * 用户相关常量
 */
public class UserConstants {


    /**
     * 用户ID前缀
     */
    public static final String USER_ID_PREFIX = "U";


    /**
     * 用户状态;u01：正常；u02：锁定；u03：封禁
     */
    public interface UserStatus {

        /**
         * 正常
         */
        String NORMAL = "U01";

        /**
         * 锁定
         */
        String LOCK = "U02";

        /**
         * 封禁
         */
        String DISABLE = "U03";
    }

    /**
     * 账号类型
     */
    public interface AccountType{

        /**
         * 用户名
         */
        String USER = "1";

        /**
         * 手机号码
         */
        String MOBILE = "2";

        /**
         * 电子邮箱
         */
        String EMAIL = "3";
    }

    /**
     * 账号状态
     */
    public interface AccountStatus{

        /**
         * 正常
         */
        String NORMAL = "1";

        /**
         * 锁定
         */
        String LOCK = "2";

        /**
         * 封禁
         */
        String DISABLE = "3";
    }
}
