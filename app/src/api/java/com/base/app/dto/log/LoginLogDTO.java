package com.base.app.dto.log;

import com.base.app.constants.FieldConstants;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class LoginLogDTO extends BaseValue {

    /**
     * 登录ip
     */
    private String loginIp;

    /**
     * 登录状态
     */
    @DictAnnotation(name = FieldConstants.Log.LOGIN_STATUS)
    private String loginStatus;

    /**
     * 登录城市
     */
    private String loginCity;

    /**
     * 登陆时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    /**
     * 登陆账号
     */
    private String loginAccount;

}
