package com.base.app.dto.user;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class UserAccountDTO extends BaseValue {

    /**
     * 用户账号id
     */
    private Long accountId;

    /**
     * 账号类型
     */
    private String accountType;

    /**
     * 账号
     */
    private String account;
}
