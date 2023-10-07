package com.base.app.service;

import com.base.app.dto.oss.OssFileDTO;
import com.base.app.dto.oss.OssSignatureDTO;
import com.base.app.ro.oss.OssFileAddRo;
import com.base.app.ro.oss.OssSignatureRo;
import com.base.app.ro.oss.SearchRo;
import com.base.core.entity.Page;

/**
 * oss文件存储接口
 */
public interface OssFileService {
    /**
     * 新增oss文件
     * @param ro
     */
    void addFile(OssFileAddRo ro);

    /**
     * 获取文件的URL
     * @param fileMd5
     * @return
     */
    String getFileUrl(String fileMd5);

    /**
     * 获取文件上传签名
     * @param ro
     * @return
     */
    OssSignatureDTO getUploadSignature(OssSignatureRo ro);

    /**
     * 分页查询oss文件列表
     * @param ro
     * @return
     */
    Page<OssFileDTO> searchPage(SearchRo ro);

}
