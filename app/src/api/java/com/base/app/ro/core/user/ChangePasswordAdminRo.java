package com.base.app.ro.core.user;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordAdminRo extends BaseRo {

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空")
    private String password;

    /**
     * 用户全局ID
     */
    @NotBlank(message = "用户全局ID不能为空")
    private String userGlobalId;
}
