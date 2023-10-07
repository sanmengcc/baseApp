package com.base.app.service;

import com.base.app.dto.user.UserPermissionsDTO;

/**
 * 权限模块
 */
public interface AuthService {

    /**
     * 根据员工ID构建登陆权限
     * @param staffId
     * @return
     */
    UserPermissionsDTO loginByStaffId(String staffId,String adminType);

    /**
     * 根据用户全局ID构建登陆权限
     * @param userGlobalId
     * @return
     */
    UserPermissionsDTO loginByUserGlobalId(String userGlobalId);
}
