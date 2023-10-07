package com.base.app.service;

/**
 * 文件存储
 */
public interface OssService {


    /**
     * 获取web直传上传签名
     *
     * @param channel    oss通道
     * @param bucketName bucket桶
     * @param path       文件路径
     * @param expiry     过期时间
     * @return
     */
    String getUploadSignature(String channel, String bucketName, String path, Integer expiry);

    /**
     * 授权文件访问签名
     *
     * @param channel    oss通道
     * @param bucketName bucket桶
     * @param path       文件路径
     * @param expiry     过期时间
     * @return
     */
    String getViewSignature(String channel, String bucketName, String path, Integer expiry);

    /**
     * 文件上传-字节流
     *
     * @param channel    oss通道
     * @param bytes      文件字节流
     * @param bucketName bucket桶
     * @param path       文件路径
     * @return
     */
    String uploadFile(String channel, byte[] bytes, String bucketName, String path);

}
