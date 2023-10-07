package com.base.app.service.impl;

import com.base.app.dao.sys.HostConfigDAO;
import com.base.app.dto.host.ConfigDTO;
import com.base.app.dto.host.HostConfigDTO;
import com.base.app.error.HostConfigErrorCode;
import com.base.app.po.sys.HostConfigPo;
import com.base.app.ro.host.AddRo;
import com.base.app.ro.host.EditRo;
import com.base.app.ro.host.SearchRo;
import com.base.app.service.HostConfigService;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.core.exception.CloudException;
import com.base.util.JsonUtils;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
public class HostConfigServiceImpl implements HostConfigService {

    @Resource
    private HostConfigDAO hostConfigDAO;

    @Override
    public Map getHostConfig(String host) {
        HostConfigPo hostConfigPo = hostConfigDAO.selectHost(host);
        if (Objects.nonNull(hostConfigPo)) {
            String configJson = hostConfigPo.getConfigJson();
            return JsonUtils.str2Map(configJson);
        }
        return null;
    }

    @Override
    public Page<HostConfigDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = hostConfigDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        Page<HostConfigDTO> search = paging.search(hostConfigDAO::selectPage, HostConfigDTO.class);
        search.getDataList().forEach(d->{
            if (ValidateHelper.isNotEmptyString(d.getConfigJson())) {
                ConfigDTO configDTO = JsonUtils.str2Bean(d.getConfigJson(), ConfigDTO.class);
                d.setSysName(configDTO.getSysName());
                d.setOemCode(configDTO.getOemCode());
            }
        });
        return search;
    }

    @Override
    public HostConfigDTO selectById(Long configId) {
        return JsonUtils.createBean(this.hostConfigDAO.queryById(configId), HostConfigDTO.class);
    }

    @Override
    public void update(EditRo ro) {
        HostConfigPo configPo = new HostConfigPo();
        configPo.setConfigId(ro.getConfigId());
        configPo.setHost(ro.getHost());
        configPo.setConfigJson(JsonUtils.toJson(ro.getConfig()));
        configPo.setGmtModified(new Date());
        this.validateHost(configPo);
        this.hostConfigDAO.update(configPo);
    }

    @Override
    public void addConfig(AddRo ro) {
        HostConfigPo configPo = new HostConfigPo();
        configPo.setHost(ro.getHost());
        configPo.setConfigJson(JsonUtils.toJson(ro.getConfig()));
        configPo.setGmtCreate(new Date());
        this.validateHost(configPo);
        this.hostConfigDAO.add(configPo);
    }

    @Override
    public void delete(Long configId) {
        this.hostConfigDAO.delete(configId);
    }

    /**
     * 校验域名
     * @param configPo
     */
    private void validateHost(HostConfigPo configPo) {
        HostConfigPo existConfig = this.hostConfigDAO.selectHost(configPo.getHost());
        if (Objects.nonNull(configPo.getConfigId())) {
            if (!existConfig.getConfigId().equals(configPo.getConfigId()) && Objects.nonNull(configPo)) {
                throw new CloudException(HostConfigErrorCode.HOST_EXIST);
            }
        }else {
            if (Objects.nonNull(existConfig)) {
                throw new CloudException(HostConfigErrorCode.HOST_EXIST);
            }
        }
    }
}
