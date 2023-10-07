package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class UserPo extends BaseValue {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 账户密码
     */
    private String password;

    /**
     * 用户名
     */
    private String userAccount;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 用户全局id
     */
    private String userGlobalId;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别;1：男；2：女；0：未知
     */
    private String sex;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态;u01：正常；u02：锁定；u03：封禁
     */
    private String status;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 删除状态;1：正常；2：删除
     */
    private String delStatus;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
