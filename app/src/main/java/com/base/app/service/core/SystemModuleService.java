package com.base.app.service.core;

import com.base.app.dto.core.module.ModuleTreeDTO;
import com.base.app.dto.core.module.SystemModuleDTO;
import com.base.app.ro.core.module.AddRo;
import com.base.app.ro.core.module.ChangeRo;
import com.base.app.ro.core.module.EditRo;
import com.base.app.ro.core.module.SearchRo;
import com.base.core.entity.Page;

import java.util.List;

public interface SystemModuleService {

    /**
     * 新增系统菜单
     *
     * @param ro
     */
    void addModule(AddRo ro);

    /**
     * 分页查询系统菜单列表
     * @param ro
     * @return
     */
    Page<SystemModuleDTO> searchPage(SearchRo ro);

    /**
     * 根据父ID查询
     * @param parentId
     * @return
     */
    List<SystemModuleDTO> selectByParentId(String parentId);

    /**
     * 获取菜单详情
     * @param moduleId
     * @return
     */
    SystemModuleDTO selectById(String moduleId);

    /**
     * 更新菜单
     * @param ro
     */
    void update(EditRo ro);

    /**
     * 删除菜单
     * @param moduleId
     */
    void delete(String moduleId);

    /**
     * 变更菜单
     * @param changeRo
     */
    void change(ChangeRo changeRo);

    /**
     * 获取菜单树
     * @return
     */
    List<ModuleTreeDTO> selectTree();

    /**
     * 查询菜单列表
     * @return
     */
    List<SystemModuleDTO> selectByList();

}
