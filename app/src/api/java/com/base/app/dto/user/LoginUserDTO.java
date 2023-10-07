package com.base.app.dto.user;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class LoginUserDTO extends BaseValue {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 真实姓名
     */
    private String realName;

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
     * 用户token
     */
    private LoginTokenDTO tokenDTO;
}
