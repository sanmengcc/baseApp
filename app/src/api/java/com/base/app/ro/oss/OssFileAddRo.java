package com.base.app.ro.oss;

import com.base.core.entity.BaseRo;
import lombok.Data;

@Data
public class OssFileAddRo extends BaseRo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Integer fileSize;

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
}
