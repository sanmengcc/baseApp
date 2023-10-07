package com.base.app.ro.role;

import com.base.core.entity.BaseValue;
import lombok.Data;


@Data
public class DeleteRo extends BaseValue {
    /**
     * 角色id
     */
    private Long roleId;
}
