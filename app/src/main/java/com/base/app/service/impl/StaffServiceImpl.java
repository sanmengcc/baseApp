package com.base.app.service.impl;

import com.base.app.constants.StaffConstants;
import com.base.app.constants.UserConstants;
import com.base.app.dao.sys.StaffArchiveDAO;
import com.base.app.dao.sys.StaffDAO;
import com.base.app.dto.log.RequestLogDTO;
import com.base.app.dto.staff.StaffDTO;
import com.base.app.dto.user.UserAccountDTO;
import com.base.app.error.StaffErrorCode;
import com.base.app.po.sys.StaffArchivePo;
import com.base.app.po.sys.StaffPo;
import com.base.app.ro.staff.AddRo;
import com.base.app.ro.staff.EditRo;
import com.base.app.ro.staff.SearchRo;
import com.base.app.ro.user.UserRegisterRo;
import com.base.app.service.StaffService;
import com.base.app.service.UserService;
import com.base.core.constant.CommonConstants;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.core.exception.CloudException;
import com.base.util.JsonUtils;
import com.base.util.UUIDGenerator;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffDAO staffDAO;
    @Resource
    private StaffArchiveDAO staffArchiveDAO;
    @Resource
    private UserService userService;

    @Override
    public Page<StaffDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = staffDAO.selectStaffCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(staffDAO::selectStaff, StaffDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addStaff(AddRo addRo) {
        String staffId = UUIDGenerator.generate();
        StaffPo staffPo = JsonUtils.createBean(addRo, StaffPo.class);
        StaffArchivePo archivePo = JsonUtils.createBean(addRo, StaffArchivePo.class);
        staffPo.setStaffId(staffId);
        archivePo.setStaffId(staffId);
        archivePo.setArchiveId(UUIDGenerator.generate());

        // 创建用户生成userGlobalId
        UserRegisterRo registerRo = this.buildRegisterRO(staffPo, archivePo);
        String userGlobalId = this.userService.register(registerRo);
        staffPo.setUserGlobalId(userGlobalId);
        staffPo.setDelStatus(CommonConstants.Del.NOT_DEL);
        staffPo.setGmtCreate(new Date());
        archivePo.setGmtCreate(new Date());
        this.staffDAO.add(staffPo);

        this.staffArchiveDAO.add(archivePo);
    }

    @Override
    public StaffDTO selectById(String staffId) {
        StaffPo staffPo = this.staffDAO.queryById(staffId);
        StaffArchivePo archivePo = this.staffArchiveDAO.selectByStaffId(staffId);
        StaffDTO staffDTO = JsonUtils.createBean(staffPo, StaffDTO.class);
        staffDTO.setEmail(archivePo.getEmail());
        staffDTO.setMobile(archivePo.getMobile());
        staffDTO.setAvatarUrl(archivePo.getAvatarUrl());
        staffDTO.setStaffName(archivePo.getStaffName());
        staffDTO.setRemark(archivePo.getRemark());
        return staffDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(EditRo editRo) {
        StaffPo existStaff = this.staffDAO.queryById(editRo.getStaffId());
        StaffArchivePo existArchive = this.staffArchiveDAO.selectByStaffId(editRo.getStaffId());
        StaffPo staffPo = JsonUtils.createBean(editRo, StaffPo.class);
        StaffArchivePo archivePo = JsonUtils.createBean(editRo, StaffArchivePo.class);
        archivePo.setArchiveId(existArchive.getArchiveId());
        staffPo.setGmtModified(new Date());
        archivePo.setGmtModified(new Date());
        UserRegisterRo registerRo = this.buildRegisterRO(staffPo, archivePo);
        registerRo.setUserGlobalId(existStaff.getUserGlobalId());
        this.staffDAO.update(staffPo);
        this.staffArchiveDAO.update(archivePo);
        this.userService.updateRegister(registerRo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String staffId, String userGlobalId) {
        StaffPo staffPo = this.staffDAO.queryById(staffId);
        if (StaffConstants.AdminType.SUPER_ADMIN.equals(staffPo.getAdminType())) {
            throw new CloudException(StaffErrorCode.SUPER_ADMIN_NOT_DELETE);
        }
        this.staffDAO.delete(staffId);
    }

    @Override
    public StaffDTO selectUserGlobalId(String userGlobalId) {
        return this.staffDAO.selectUserGlobalId(userGlobalId);
    }

    /**
     * 创建用户信息
     *
     * @param archivePo
     * @return
     */
    public UserRegisterRo buildRegisterRO(StaffPo staffPo, StaffArchivePo archivePo) {
        UserRegisterRo registerRo = new UserRegisterRo();

        // 处理多个账号信息
        List<UserAccountDTO> accountDTOS = new ArrayList<>();
        registerRo.setAccounts(accountDTOS);
        UserAccountDTO userAccount = new UserAccountDTO();
        userAccount.setAccountType(UserConstants.AccountType.USER);
        userAccount.setAccount(staffPo.getAccount());
        accountDTOS.add(userAccount);
        if (ValidateHelper.isNotEmptyString(archivePo.getMobile())) {
            UserAccountDTO mobileAccount = new UserAccountDTO();
            mobileAccount.setAccountType(UserConstants.AccountType.MOBILE);
            mobileAccount.setAccount(archivePo.getMobile());
            accountDTOS.add(mobileAccount);
        }

        if (ValidateHelper.isNotEmptyString(archivePo.getEmail())) {
            UserAccountDTO emailAccount = new UserAccountDTO();
            emailAccount.setAccountType(UserConstants.AccountType.EMAIL);
            emailAccount.setAccount(archivePo.getEmail());
            accountDTOS.add(emailAccount);
        }
        registerRo.setRealName(archivePo.getStaffName());
        registerRo.setMobile(archivePo.getMobile());
        registerRo.setEmail(archivePo.getEmail());
        registerRo.setUserAccount(staffPo.getAccount());
       return registerRo;
    }
}
