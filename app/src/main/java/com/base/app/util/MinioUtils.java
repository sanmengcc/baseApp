package com.base.app.util;

import com.base.app.config.oss.MinioConfig;
import com.base.app.error.OssErrorCode;
import com.base.core.exception.CloudException;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MinioUtils {

    @Resource
    private MinioClient minioClient;
    @Resource
    private MinioConfig minioConfig;

    /**
     * 获取上传文件签名
     * @param bucketName
     * @param path
     * @param expiry
     * @return
     */
    public String getUploadSignature(String bucketName, String path, Integer expiry) {
        try {
            Map<String, String> reqParams = new HashMap<String, String>();
            reqParams.put("response-content-type", "application/json");
            String product = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.PUT)
                            .bucket(bucketName)
                            .object(path)
                            .expiry(expiry)
                            .extraQueryParams(reqParams)
                            .build());
            return product;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new CloudException(OssErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取访问签名
     * @param bucketName
     * @param path
     * @param expiry
     * @return
     */
    public String getViewSignature(String bucketName, String path, Integer expiry) {
        try {
            String product = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(path)
                    .method(Method.GET)
                    .expiry(expiry)
                    .build());
            return product;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CloudException(OssErrorCode.SYSTEM_ERROR);
        }

    }

    /**
     * 字节流文件上传-主要用于内部调用
     * @param bytes
     * @param bucketName
     * @param path
     * @return
     */
    public String uploadFile(byte[] bytes,String bucketName, String path) {

        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(path).stream(
                                    bis, bis.available(), -1)
                            .build());
            bis.close();
            return minioConfig.getEndpoint() + "/" + bucketName + "/" + path;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CloudException(OssErrorCode.SYSTEM_ERROR);
        }
    }
}
