package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class UserLoginLogVo extends BaseValue {
    /**
     * 登陆id
     */
    private Long loginLogId;

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录状态
     */
    private String loginStatus;

    /**
     * 登录城市
     */
    private String loginCity;

    /**
     * 登陆时间
     */
    private Date loginTime;

    /**
     * 登陆账号
     */
    private String loginAccount;

    /**
     * 登陆参数
     */
    private String loginData;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
