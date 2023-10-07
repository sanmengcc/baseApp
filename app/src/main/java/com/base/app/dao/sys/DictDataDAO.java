package com.base.app.dao.sys;

import com.base.app.po.sys.DictDataPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictDataDAO extends BaseDAO<DictDataPo> {

    /**
     * 查询字典
     * @param dictType
     * @return
     */
    List<DictDataPo> selectByDictType(String dictType);
}
