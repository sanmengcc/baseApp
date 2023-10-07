package com.base.app.service.impl;

import com.base.app.dao.sys.OemInfoDAO;
import com.base.app.dto.oem.OemInfoDTO;
import com.base.app.error.OemInfoErrorCode;
import com.base.app.po.sys.OemInfoPo;
import com.base.app.ro.oem.AddRo;
import com.base.app.ro.oem.EditRo;
import com.base.app.ro.oem.SearchRo;
import com.base.app.service.OemInfoService;
import com.base.core.constant.CommonConstants;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.core.exception.CloudException;
import com.base.util.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OemInfoServiceImpl implements OemInfoService {

    @Resource
    private OemInfoDAO oemInfoDAO;

    @Override
    public void addOem(AddRo ro) {
        OemInfoPo oemInfoPo = JsonUtils.createBean(ro, OemInfoPo.class);
        this.validateOemCodeExist(oemInfoPo);
        oemInfoPo.setDelStatus(CommonConstants.Del.NOT_DEL);
        oemInfoPo.setGmtCreate(new Date());
        this.oemInfoDAO.add(oemInfoPo);
    }

    @Override
    public void update(EditRo ro) {
        OemInfoPo oemInfoPo = JsonUtils.createBean(ro, OemInfoPo.class);
        this.validateOemCodeExist(oemInfoPo);
        oemInfoPo.setGmtModified(new Date());
        this.oemInfoDAO.update(oemInfoPo);
    }

    @Override
    public void delete(Long oemId) {
        this.oemInfoDAO.delete(oemId);
    }

    @Override
    public Page<OemInfoDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = oemInfoDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(oemInfoDAO::selectPage, OemInfoDTO.class);
    }

    @Override
    public OemInfoDTO selectById(Long oemId) {
        return JsonUtils.createBean(this.oemInfoDAO.queryById(oemId), OemInfoDTO.class);
    }

    @Override
    public List<OemInfoDTO> selectList() {
        return JsonUtils.createList(this.oemInfoDAO.selectNoPage(new Paging()), OemInfoDTO.class);
    }

    /**
     * 校验编码是否存在
     * @param oemInfoPo
     */
    private void validateOemCodeExist(OemInfoPo oemInfoPo) {
        OemInfoPo existPO = this.oemInfoDAO.selectOemCode(oemInfoPo.getOemCode());
        if (Objects.nonNull(oemInfoPo.getOemId()) && !existPO.getOemId().equals(oemInfoPo.getOemId())) {
            throw new CloudException(OemInfoErrorCode.OEM_CODE_EXIST);
        }
        if (Objects.isNull(oemInfoPo.getOemId()) && Objects.nonNull(existPO)) {
            throw new CloudException(OemInfoErrorCode.OEM_CODE_EXIST);
        }
    }
}
