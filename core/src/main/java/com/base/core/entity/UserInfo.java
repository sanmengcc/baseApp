package com.base.core.entity;

import lombok.Data;

@Data
public class UserInfo extends BaseValue{

    /**
     * 用户账号
     */
    private String account;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 用户全局ID
     */
    private String userGlobalId;
}
