package com.base.app.service;

import com.base.app.vo.sys.DictDataVo;

import java.util.List;
import java.util.Map;

/**
 * 数据字典接口
 */
public interface DictService {

    /**
     * 查询数据字典
     * @param dictType
     * @return
     */
    List<DictDataVo> selectByDictType(String dictType);

    /**
     * 获取数据字典组
     * @param dictTypes
     * @return
     */
    Map<String, List<DictDataVo>> selectByDictTypes(String dictTypes);
}
