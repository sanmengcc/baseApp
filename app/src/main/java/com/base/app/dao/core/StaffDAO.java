package com.base.app.dao.core;

import com.base.app.dto.core.staff.StaffDTO;
import com.base.app.po.core.StaffPo;
import com.base.core.entity.Paging;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffDAO extends BaseDAO<StaffPo> {

    /**
     * 分页查询员工总数
     * @param paging
     * @return
     */
    Long selectStaffCount(Paging paging);

    /**
     * 分页查询员工列表
     * @param paging
     * @return
     */
    List<StaffDTO> selectStaff(Paging paging);

    /**
     * 根据用户全局ID查询员工DTO
     * @param userGlobalId
     * @return
     */
    StaffDTO selectUserGlobalId(String userGlobalId);

    /**
     * 根据用户名查询
     * @param account
     * @return
     */
    StaffPo selectByAccount(String account);
}
