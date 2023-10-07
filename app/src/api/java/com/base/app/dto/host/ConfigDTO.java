package com.base.app.dto.host;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class ConfigDTO extends BaseValue {

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 网站图标
     */
    private String favicon;

    /**
     * 租户编码
     */
    private String oemCode;

    /**
     * 登录页背景图
     */
    private String backgroundImage;

    /**
     * 登录页特效图
     */
    private String loginLeftImage;

    /**
     * 登录页LOGO
     */
    private String loginLogo;

    /**
     * 登陆表单文案
     */
    private String loginTitle;

    /**
     * 版权信息
     */
    private String copyright;
}
