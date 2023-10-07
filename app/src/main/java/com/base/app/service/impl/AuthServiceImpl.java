package com.base.app.service.impl;

import com.base.app.constants.StaffConstants;
import com.base.app.dto.module.SystemModuleDTO;
import com.base.app.dto.staff.StaffDTO;
import com.base.app.dto.user.UserPermissionsDTO;
import com.base.app.dto.user.VueRouterModuleVo;
import com.base.app.enums.ModuleEnum;
import com.base.app.error.StaffErrorCode;
import com.base.app.service.AuthService;
import com.base.app.service.RoleService;
import com.base.app.service.StaffService;
import com.base.app.service.SystemModuleService;
import com.base.app.util.RouterUtils;
import com.base.core.constant.CommonConstants;
import com.base.core.exception.CloudException;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private RoleService roleService;
    @Resource
    private SystemModuleService systemModuleService;
    @Resource
    private StaffService staffService;

    @Override
    public UserPermissionsDTO loginByStaffId(String staffId,String adminType) {
        // 用户权限
        UserPermissionsDTO permissions = new UserPermissionsDTO();
        // 员工菜单
        List<SystemModuleDTO> moduleDTOS = this.selectStaffHasModule(staffId, adminType);
        // 抽取按钮权限
        List<String> authButtonCodes = moduleDTOS.stream()
                .filter(module -> ModuleEnum.Type.BUTTON.getCode().equals(module.getType()))
                .map(SystemModuleDTO::getAuthCode)
                .distinct()
                .collect(Collectors.toList());
        permissions.setButtonCodes(authButtonCodes);
        Map<String, List<SystemModuleDTO>> parentMap = moduleDTOS.stream()
                .filter(module -> !ModuleEnum.Type.BUTTON.getCode().equals(module.getType()))
                .collect(Collectors.groupingBy(SystemModuleDTO::getParentId));
        // 构建Vue菜单
        List<SystemModuleDTO> menuList = moduleDTOS.stream()
                .filter(module -> !ModuleEnum.Type.BUTTON.getCode().equals(module.getType()))
                .map(module->{
                    // 重置
                    List<SystemModuleDTO> subList = parentMap.get(module.getModuleId());
                    if (ValidateHelper.isEmptyCollection(subList)) {
                        module.setHasChild(CommonConstants.Flag.NO);
                    }
                    return module;
                })
                .collect(Collectors.toList());
        List<VueRouterModuleVo> router = RouterUtils.buildVueRouter(menuList);
        List<String> requestURL = moduleDTOS.stream()
                .filter(module -> ValidateHelper.isNotEmptyString(module.getServerApi()))
                .map(SystemModuleDTO::getServerApi)
                .distinct()
                .collect(Collectors.toList());
        permissions.setRequestURL(requestURL);
        permissions.setModuleList(router);

        return permissions;
    }

    @Override
    public UserPermissionsDTO loginByUserGlobalId(String userGlobalId) {
        StaffDTO staffDTO = this.staffService.selectUserGlobalId(userGlobalId);
        if (Objects.isNull(staffDTO)) {
            throw new CloudException(StaffErrorCode.STAFF_IS_NOT_EXIST);
        }
        return this.loginByStaffId(staffDTO.getStaffId(), staffDTO.getAdminType());
    }

    /**
     * 获取员工的菜单列表
     * @param staffId
     * @return
     */
    private List<SystemModuleDTO> selectStaffHasModule(String staffId,String adminType) {
        // 所有的菜单数据
        List<SystemModuleDTO> moduleDTOS = this.systemModuleService.selectByList();
        // 超级管理员获取所有菜单权限
        if (StaffConstants.AdminType.SUPER_ADMIN.equals(adminType)) {
            return moduleDTOS;
        }

        // 员工已有的数据
        List<String> moduleIdList = this.roleService.selectStaffHasModule(staffId);

        List<SystemModuleDTO> dataList = new ArrayList<>();
        // 筛选菜单
        moduleDTOS.forEach(module->{
            if (moduleIdList.contains(module.getModuleId())) {
                moduleDTOS.add(module);
            }
            // 向叶子节点寻找
            this.buildChildModule(module.getModuleId(), dataList, moduleDTOS);
            // 向顶级节点寻找
            this.buildParentModule(module.getParentId(), dataList, moduleDTOS);
        });

        return dataList;
    }

    public void buildChildModule(String moduleId, List<SystemModuleDTO> target, List<SystemModuleDTO> moduleDTOS) {
        moduleDTOS.forEach(module->{
            if (module.getParentId().equals(moduleId)) {
                target.add(module);
                this.buildChildModule(module.getModuleId(), target, moduleDTOS);
            }
        });
    }

    public void buildParentModule(String parentId, List<SystemModuleDTO> target, List<SystemModuleDTO> moduleDTOS) {
        moduleDTOS.forEach(module -> {
            if (module.getModuleId().equals(parentId)) {
                target.add(module);
                this.buildParentModule(module.getParentId(), target, moduleDTOS);
            }
        });
    }
}
