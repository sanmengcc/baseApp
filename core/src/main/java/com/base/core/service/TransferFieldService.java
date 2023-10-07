package com.base.core.service;

import java.util.Map;

public interface TransferFieldService {

    /**
     * 获取转换数据
     * @param mappingType
     * @return
     */
    Map<String, String> getMapping(String mappingType);
}
