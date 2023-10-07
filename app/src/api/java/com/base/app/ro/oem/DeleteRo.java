package com.base.app.ro.oem;

import com.base.core.entity.BaseRo;
import lombok.Data;


@Data
public class DeleteRo extends BaseRo {
    /**
     * 租户id
     */
    private Long oemId;
}
