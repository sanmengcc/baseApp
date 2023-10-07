package com.base.app.dto.oss;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class OssSignatureDTO extends BaseValue {

    /**
     * 签名
     */
    private String signature;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 是否存在
     */
    private boolean exist = false;
}
