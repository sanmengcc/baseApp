package com.base.app.ro.user;

import com.base.core.entity.BaseRo;
import lombok.Data;

@Data
public class ChangePasswordAdminRo extends BaseRo {

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户全局ID
     */
    private String userGlobalId;
}
