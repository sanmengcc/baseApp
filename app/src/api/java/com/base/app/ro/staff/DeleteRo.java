package com.base.app.ro.staff;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class DeleteRo extends BaseValue {

    /**
     * 员工ID
     */
    private String staffId;

    /**
     * 用户全局ID
     */
    private String userGlobalId;
}
