package com.base.app.service.impl;

import com.base.app.dao.sys.SystemModuleDAO;
import com.base.app.dto.module.ModuleTreeDTO;
import com.base.app.dto.module.SystemModuleDTO;
import com.base.app.po.sys.SystemModulePo;
import com.base.app.ro.module.AddRo;
import com.base.app.ro.module.ChangeRo;
import com.base.app.ro.module.EditRo;
import com.base.app.ro.module.SearchRo;
import com.base.app.service.SystemModuleService;
import com.base.core.constant.CommonConstants;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.util.JsonUtils;
import com.base.util.TreeUtils;
import com.base.util.UUIDGenerator;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SystemModuleServiceImpl implements SystemModuleService {
    @Resource
    private SystemModuleDAO systemModuleDAO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addModule(AddRo ro) {
        SystemModulePo systemModulePo = JsonUtils.createBean(ro, SystemModulePo.class);
        systemModulePo.setModuleId(UUIDGenerator.generate());
        systemModulePo.setDelStatus(CommonConstants.Del.NOT_DEL);
        systemModulePo.setHasChild(CommonConstants.Flag.NO);
        systemModulePo.setGmtCreate(new Date());
        // 更新父节点
        if (!CommonConstants.PARENT_ID.equals(systemModulePo.getParentId())) {
            // 修改hasChild
            this.systemModuleDAO.updateHasChild(systemModulePo.getParentId(), CommonConstants.Flag.YES);
        }
        this.systemModuleDAO.add(systemModulePo);
    }

    @Override
    public Page<SystemModuleDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = systemModuleDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        Page<SystemModuleDTO> page = paging.search(systemModuleDAO::selectPage, SystemModuleDTO.class);
        page.getDataList()
                .forEach(d -> {
                    if (CommonConstants.Flag.YES.equals(d.getHasChild())) {
                        d.setHasChildren(true);
                    }
                });
        return page;
    }

    @Override
    public List<SystemModuleDTO> selectByParentId(String parentId) {
        List<SystemModulePo> poList = this.systemModuleDAO.selectByParentId(parentId);
        List<SystemModuleDTO> dataList = JsonUtils.createList(poList, SystemModuleDTO.class);
        dataList.forEach(d -> {
            if (CommonConstants.Flag.YES.equals(d.getHasChild())) {
                d.setHasChildren(true);
            }
        });
        return dataList;
    }

    @Override
    public SystemModuleDTO selectById(String moduleId) {
        SystemModulePo systemModulePo = this.systemModuleDAO.queryById(moduleId);
        return JsonUtils.createBean(systemModulePo, SystemModuleDTO.class);
    }

    @Override
    public void update(EditRo ro) {
        SystemModulePo systemModulePo = this.systemModuleDAO.queryById(ro.getModuleId());
        if (Objects.nonNull(systemModulePo)) {
            JsonUtils.copyBeanNotNull2Bean(ro, systemModulePo);
            systemModulePo.setGmtModified(new Date());
            this.systemModuleDAO.update(systemModulePo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String moduleId) {
        SystemModulePo po = this.systemModuleDAO.queryById(moduleId);
        if (Objects.nonNull(po)) {
            this.systemModuleDAO.delete(moduleId);
            // 处理hasChild
            if (ValidateHelper.isEmptyCollection(this.systemModuleDAO.selectByParentId(po.getParentId()))) {
                this.systemModuleDAO.updateHasChild(po.getParentId(), CommonConstants.Flag.NO);
            }
        }
    }

    @Override
    public void change(ChangeRo changeRo) {
        this.update(JsonUtils.createBean(changeRo, EditRo.class));
    }

    @Override
    public List<ModuleTreeDTO> selectTree() {
        List<SystemModulePo> poList = this.systemModuleDAO.selectNoPage(new Paging());
        List<ModuleTreeDTO> dataList = JsonUtils.createList(poList, ModuleTreeDTO.class);
        dataList.forEach(d -> {
            if (CommonConstants.Flag.NO.equals(d.getHasChild())) {
                d.setIsLeaf(true);
            }
        });
        TreeUtils<ModuleTreeDTO> builder = new TreeUtils<>() {
            @Override
            protected String getKey(ModuleTreeDTO node) {
                return node.getModuleId();
            }

            @Override
            protected String getParentId(ModuleTreeDTO node) {
                return node.getParentId();
            }

            @Override
            protected List<ModuleTreeDTO> getChildren(ModuleTreeDTO node) {
                return node.getChildren();
            }

            @Override
            protected void setChildren(List<ModuleTreeDTO> nodes, ModuleTreeDTO node) {
                node.setChildren(nodes);
            }
        };
        return builder.getTree(dataList);
    }

    @Override
    public List<SystemModuleDTO> selectByList() {
        return JsonUtils.createList(this.systemModuleDAO.selectNoPage(new Paging()), SystemModuleDTO.class);
    }

}
