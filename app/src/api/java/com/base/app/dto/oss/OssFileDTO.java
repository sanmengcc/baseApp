package com.base.app.dto.oss;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OssFileDTO extends BaseValue {

    /**
     * 文件id
     */
    private Long fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 存储桶
     */
    private String bucketName;

    /**
     * 文件地址
     */
    private String fileUrl;

    /**
     * 文件md5
     */
    private String fileMd5;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
