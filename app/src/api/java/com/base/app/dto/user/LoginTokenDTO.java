package com.base.app.dto.user;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class LoginTokenDTO extends BaseValue {

    /**
     * token
     */
    private String access_token;

    /**
     * 过期时间
     */
    private Long expires_in;

}
