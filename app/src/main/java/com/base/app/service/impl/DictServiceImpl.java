package com.base.app.service.impl;

import com.base.app.constants.CacheConstants;
import com.base.app.dao.sys.DictDataDAO;
import com.base.app.service.DictService;
import com.base.app.service.RedisService;
import com.base.app.vo.sys.DictDataVo;
import com.base.util.JsonUtils;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DictServiceImpl implements DictService {

    @Resource
    private RedisService redisService;
    @Resource
    private DictDataDAO dictDataDAO;

    @Override
    public List<DictDataVo> selectByDictType(String dictType) {
        String redisKey = CacheConstants.DictData.PREX + dictType;
        Object value = redisService.getValue(redisKey);
        if (Objects.nonNull(value)) {
            return JsonUtils.str2List(value.toString(), DictDataVo.class);
        }
        List<DictDataVo> dataList = JsonUtils.createList(dictDataDAO.selectByDictType(dictType), DictDataVo.class);
        if (ValidateHelper.isNotEmptyCollection(dataList)) {
            redisService.setValue(redisKey, JsonUtils.toJson(dataList), CacheConstants.DictData.TIME);
        }
        return dataList;
    }

    @Override
    public Map<String, List<DictDataVo>> selectByDictTypes(String dictTypes) {
        Map<String, List<DictDataVo>> dictMap = new HashMap<>();
        String[] dictTypeArr = dictTypes.split(",");
        for (String dictType : dictTypeArr) {
            dictMap.put(dictType, this.selectByDictType(dictType));
        }
        return dictMap;
    }
}
