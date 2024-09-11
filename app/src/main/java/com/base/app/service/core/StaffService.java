package com.base.app.service.core;

import com.base.app.dto.core.staff.StaffDTO;
import com.base.app.ro.core.staff.AddRo;
import com.base.app.ro.core.staff.EditRo;
import com.base.app.ro.core.staff.SearchRo;
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
