package com.base.app.ro.log;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class LoginLogPageRo extends BaseValue {

    /**
     * 登陆账号
     */
    private String account;

    /**
     * 登陆城市
     */
    private String loginCity;

    /**
     * 登陆状态
     */
    private String loginStatus;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
