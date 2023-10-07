package com.base.app.service.impl;

import com.base.app.config.oss.MinioConfig;
import com.base.app.dao.sys.OssFileDAO;
import com.base.app.dto.oss.OssFileDTO;
import com.base.app.dto.oss.OssSignatureDTO;
import com.base.app.po.sys.OssFilePo;
import com.base.app.ro.oss.OssFileAddRo;
import com.base.app.ro.oss.OssSignatureRo;
import com.base.app.ro.oss.SearchRo;
import com.base.app.service.OssFileService;
import com.base.app.service.OssService;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.util.JsonUtils;
import com.base.util.OssBucketUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class OssFileServiceImpl implements OssFileService {

    @Resource
    private OssService ossService;
    @Resource
    private OssFileDAO ossFileDAO;
    @Resource
    private MinioConfig minioConfig;

    @Override
    public void addFile(OssFileAddRo ro) {
        if (Objects.isNull(this.getFileUrl(ro.getFileMd5()))) {
            OssFilePo ossFilePo = JsonUtils.createBean(ro, OssFilePo.class);
            ossFilePo.setGmtCreate(new Date());
            ossFilePo.setBucketName(minioConfig.getBucketName());
            ossFilePo.setProtocol(minioConfig.getProtocol());
            ossFileDAO.add(ossFilePo);
        }
    }

    @Override
    public String getFileUrl(String fileMd5) {
        return Optional.ofNullable(ossFileDAO.queryByMd5(fileMd5)).map(OssFilePo::getFileUrl).orElse(null);
    }

    @Override
    public OssSignatureDTO getUploadSignature(OssSignatureRo ro) {
        String bucketName = OssBucketUtils.generateBucketName(minioConfig.getBucketName(), ro.isBucketPrivate());
        String path = OssBucketUtils.generatePath(ro.getFileName(), ro.isKeepOrigName());
        OssSignatureDTO signatureDTO = new OssSignatureDTO();
        // 通过MD值查询数据库是否存在
        OssFilePo ossFilePo = this.ossFileDAO.queryByMd5(ro.getMd5());
        if (Objects.nonNull(ossFilePo)) {
            signatureDTO.setExist(true);
            signatureDTO.setUrl(ossFilePo.getFileUrl());
            return signatureDTO;
        }
        // 申请签名
        String uploadSignature = ossService.getUploadSignature(minioConfig.getChannel(), bucketName, path, minioConfig.getExpiry());
        signatureDTO.setSignature(uploadSignature);
        // 转换请求URL
        signatureDTO.setUrl(OssBucketUtils.getURL(uploadSignature));
        return signatureDTO;
    }

    @Override
    public Page<OssFileDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = ossFileDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(ossFileDAO::selectPage, OssFileDTO.class);
    }
}
