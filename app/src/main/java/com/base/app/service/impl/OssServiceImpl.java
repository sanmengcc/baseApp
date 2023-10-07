package com.base.app.service.impl;

import com.base.app.error.OssErrorCode;
import com.base.app.service.OssService;
import com.base.app.util.MinioUtils;
import com.base.core.constant.SystemConstants;
import com.base.core.exception.CloudException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OssServiceImpl implements OssService {

    @Resource
    private MinioUtils minioUtils;


    @Override
    public String getUploadSignature(String channel, String bucketName, String path, Integer expiry) {
        switch (channel) {
            // MINIO模式
            case SystemConstants.OssChannel.MINIO:
                return minioUtils.getUploadSignature(bucketName, path, expiry);
            default:
                break;
        }
        throw new CloudException(OssErrorCode.CHANNEL_NOT_EXIST);
    }

    @Override
    public String getViewSignature(String channel, String bucketName, String path, Integer expiry) {
        switch (channel) {
            case SystemConstants.OssChannel.MINIO:
                return minioUtils.getViewSignature(bucketName, path, expiry);
            default:
                break;
        }
        throw new CloudException(OssErrorCode.CHANNEL_NOT_EXIST);
    }

    @Override
    public String uploadFile(String channel, byte[] bytes, String bucketName, String path) {
        switch (channel) {
            case SystemConstants.OssChannel.MINIO:
                return minioUtils.uploadFile(bytes, bucketName, path);
            default:
                break;
        }
        throw new CloudException(OssErrorCode.CHANNEL_NOT_EXIST);
    }


}
