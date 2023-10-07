package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class UserAccountPo extends BaseValue {
    /**
     * 用户账号id
     */
    private Long accountId;

    /**
     * 用户全局id
     */
    private String userGlobalId;

    /**
     * 账户信息
     */
    private String account;

    /**
     * 账号类型
     */
    private String accountType;

    /**
     * 账号状态;1：正常；2：锁定；3：封禁
     */
    private String status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
