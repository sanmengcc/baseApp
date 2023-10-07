package com.base.app.service;

import com.base.app.dto.oem.OemInfoDTO;
import com.base.app.ro.oem.AddRo;
import com.base.app.ro.oem.EditRo;
import com.base.app.ro.oem.SearchRo;
import com.base.core.entity.Page;

import java.util.List;

/**
 * 租户相关接口
 */
public interface OemInfoService {

    /**
     * 新增租户信息
     * @param ro
     */
    void addOem(AddRo ro);

    /**
     * 修改租户信息
     * @param ro
     */
    void update(EditRo ro);

    /**
     * 刪除租户信息
     * @param oemId
     */
    void delete(Long oemId);

    /**
     * 分页查询租户信息
     * @param ro
     * @return
     */
    Page<OemInfoDTO> searchPage(SearchRo ro);

    /**
     * 查询租户信息
     * @param oemId
     * @return
     */
    OemInfoDTO selectById(Long oemId);

    /**
     * 查询租户列表
     * @return
     */
    List<OemInfoDTO> selectList();

}
