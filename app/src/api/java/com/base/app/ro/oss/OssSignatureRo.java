package com.base.app.ro.oss;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * oss签名
 */
@Data
public class OssSignatureRo extends BaseRo {

    /**
     * 文件名称
     */
    @NotNull(message = "文件名称不能为空")
    private String fileName;

    /**
     * bucketName的权限
     */
    private boolean bucketPrivate = false;

    /**
     * 是否保持原有文件名称
     */
    private boolean keepOrigName = false;

    /**
     * 文件MD5
     */
    private String md5;

    /**
     * 文件大小
     */
    private Integer fileSize;
}
