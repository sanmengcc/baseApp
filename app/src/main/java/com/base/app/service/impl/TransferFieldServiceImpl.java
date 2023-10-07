package com.base.app.service.impl;

import com.base.app.service.DictService;
import com.base.core.service.TransferFieldService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransferFieldServiceImpl implements TransferFieldService {

    @Resource
    private DictService dictService;

    @Override
    public Map<String, String> getMapping(String mappingType) {
       return dictService.selectByDictType(mappingType)
                .stream()
                .collect(Collectors.toMap(d -> d.getDictKey(), d -> d.getDictValue()));
    }
}
