package com.base.app.config.oss;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "oss.minio")
@Data
public class MinioConfig {

    /**
     * api地址
     */
    private String endpoint;

    /**
     * 密钥
     */
    private String secretKey;

    /**
     * 私钥
     */
    private String accessKey;

    /**
     * 过期时间
     */
    private Integer expiry;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * oss渠道
     */
    private String channel;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
    }
}
