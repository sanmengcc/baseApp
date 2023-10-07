package com.base.app.dao.sys;

import com.base.app.dto.staff.StaffDTO;
import com.base.app.po.sys.StaffPo;
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
}
