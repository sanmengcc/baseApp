package com.base.app.constants;

/**
 * 字段注解
 */
public class FieldConstants {

    /**
     * 通用
     */
    public interface Common{

        /**
         * 是否有子级
         */
        String HAS_CHILD = "HAS_CHILD";

        /**
         * 启用状态
         */
        String ENABLE_STATUS = "ENABLE_STATUS";

        /**
         * 显示状态
         */
        String HIDDEN_STATUS = "HIDDEN_STATUS";

        /**
         * 标识
         */
        String FLAG = "COMMON_FLAG";
    }

    /**
     * 菜单模块
     */
    public interface Module{

        /**
         * 菜单模块类型
         */
        String TYPE = "MODULE_TYPE";
    }

    /**
     * 员工模块
     */
    public interface Staff{

        /**
         * 在职状态
         */
        String WORK_STATUS = "WORK_STATUS";

        /**
         * 员工类型
         */
        String ADMIN_TYPE = "ADMIN_TYPE";
    }

    /**
     * 角色模块
     */
    public interface Role{

        /**
         * 角色类型
         */
        String TYPE = "ROLE_TYPE";
    }

    /**
     * 日志相关
     */
    public interface Log{

        /**
         * 登录状态
         */
        String LOGIN_STATUS = "LOGIN_STATUS";
    }

    /**
     * 用户相关
     */
    public interface User{

        /**
         * 用户状态
         */
        String USER_STATUS = "USER_STATUS";
    }

    /**
     * 租户相关
     */
    public interface Oem {

        /**
         * 租戶狀態
         */
        String OEM_STATUS = "OEM_STATUS";

    }
}
