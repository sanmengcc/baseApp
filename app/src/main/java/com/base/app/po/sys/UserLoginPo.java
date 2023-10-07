package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class UserLoginPo extends BaseValue {
    /**
     * 登陆id
     */
    private Long loginId;

    /**
     * 用户id
     */
    private String userGlobalId;

    /**
     * 修改凭证时间
     */
    private Date changeDate;

    /**
     * 连续错误次数
     */
    private Long loginErrorCount;

    /**
     * 上一次登录ip
     */
    private String lastLoginIp;

    /**
     * 上一次登录城市
     */
    private String lastLoginCity;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
