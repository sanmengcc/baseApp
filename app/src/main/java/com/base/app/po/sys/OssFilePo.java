package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class OssFilePo extends BaseValue {

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
     * 创建人id
     */
    private String creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
