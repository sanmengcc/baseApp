package com.base.app.ro.user;

import com.base.app.dto.user.UserAccountDTO;
import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserRegisterRo extends BaseValue {

    /**
     * 用户全局ID
     */
    private String userGlobalId;

    /**
     * 账号信息
     */
    private List<UserAccountDTO> accounts;

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
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别;1：男；2：女；0：未知
     */
    private String sex;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 手机号
     */
    private String mobile;
}
