package com.base.app.dao.sys;

import com.base.app.po.sys.SystemModulePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemModuleDAO extends BaseDAO<SystemModulePo> {

    /**
     * 更新子级
     * @param moduleId
     * @param hasChild
     */
    void updateHasChild(String moduleId, String hasChild);

    /**
     * 根据父ID查询
     * @param parentId
     * @return
     */
    List<SystemModulePo> selectByParentId(String parentId);
}
