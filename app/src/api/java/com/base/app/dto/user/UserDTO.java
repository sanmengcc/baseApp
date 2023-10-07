package com.base.app.dto.user;

import com.base.app.constants.FieldConstants;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserDTO extends BaseValue {
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
    @DictAnnotation(name = FieldConstants.User.USER_STATUS)
    private String status;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
