package com.base.app.service;

import com.base.app.dto.host.HostConfigDTO;
import com.base.app.ro.host.AddRo;
import com.base.app.ro.host.EditRo;
import com.base.app.ro.host.SearchRo;
import com.base.core.entity.Page;

import java.util.Map;

/**
 * 域名配置
 */
public interface HostConfigService {

    /**
     * 获取域名配置
     * @param host
     * @return
     */
    Map getHostConfig(String host);

    /**
     * 分页查询域名配置列表
     * @param ro
     * @return
     */
    Page<HostConfigDTO> searchPage(SearchRo ro);

    /**
     * 查询域名配置信息
     * @param configId
     * @return
     */
    HostConfigDTO selectById(Long configId);

    /**
     * 修改域名配置信息
     * @param ro
     */
    void update(EditRo ro);

    /**
     * 新增域名配置信息
     * @param ro
     */
    void addConfig(AddRo ro);

    /**
     * 删除域名配置信息
     * @param configId
     */
    void delete(Long configId);
}
