package com.base.app.api.sys;

import com.base.app.dto.dict.DictDTO;
import com.base.app.service.DictService;
import com.base.app.vo.sys.DictDataVo;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import com.base.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@Api(name = "数据字典")
@RestController
@RequestMapping(value = "/api/dict")
public class DictAPI extends BaseAPI {

    @Resource
    private DictService dictService;

    @Api(name = "获取数据字典组")
    @ApiPermission(auth = false)
    @GetMapping(value = "/getDict")
    public R getDict(String dictType){
        List<DictDataVo> dataList = dictService.selectByDictType(dictType);
        return R.ok(JsonUtils.createList(dataList, DictDTO.class));
    }

    @Api(name = "获取数据字典组")
    @ApiPermission(auth = false)
    @GetMapping(value = "/getDicMap")
    public R getDicMap(String dictTypes){
        Map<String, List<DictDataVo>> dictMap = dictService.selectByDictTypes(dictTypes);
        return R.ok(dictMap);
    }
}
