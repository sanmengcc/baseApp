package com.base.app.service;

import com.base.app.dto.staff.StaffDTO;
import com.base.app.ro.staff.AddRo;
import com.base.app.ro.staff.EditRo;
import com.base.app.ro.staff.SearchRo;
import com.base.core.entity.Page;

/**
 * 员工相关接口
 */
public interface StaffService {

    /**
     * 分页查询员工数据
     * @param ro
     * @return
     */
    Page<StaffDTO> searchPage(SearchRo ro);

    /**
     * 新增员工
     * @param addRo
     */
    void addStaff(AddRo addRo);

    /**
     * 获取员工详情
     * @param staffId
     * @return
     */
    StaffDTO selectById(String staffId);

    /**
     * 修改员工资料
     * @param editRo
     */
    void update(EditRo editRo);

    /**
     * 删除员工
     * @param staffId
     * @param userGlobalId
     */
    void delete(String staffId, String userGlobalId);

    /**
     * 根据用户全局ID查询员工信息
     * @param userGlobalId
     * @return
     */
    StaffDTO selectUserGlobalId(String userGlobalId);

}
