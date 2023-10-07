package com.base.app.constants;

/**
 * 登录常量
 */
public class LoginConstants {

    /**
     * 登录方式
     */
    public interface LoginType {

        /**
         * 密码登录
         */
        String PASSWORD = "1";
    }

    /**
     * token相关
     */
    public interface Token{

        /**
         * token ID
         */
        String TOKEN_ID = "token_id:";

        /**
         * token用户信息
         */
        String TOKEN_USER = "token_user:";

    }

    /**
     * 登录状态
     */
    public interface LoginStatus{

        /**
         * 登录成功
         */
        String SUCCESS = "1";

        /**
         * 登录失败
         */
        String FAIL = "2";
    }
}
