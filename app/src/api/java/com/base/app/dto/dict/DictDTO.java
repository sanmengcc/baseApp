package com.base.app.dto.dict;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class DictDTO extends BaseValue {

    /**
     * 字典值
     */
    private String dictKey;

    /**
     * 名称
     */
    private String dictValue;
}
