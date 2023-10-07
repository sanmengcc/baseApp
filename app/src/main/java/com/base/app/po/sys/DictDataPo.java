package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class DictDataPo extends BaseValue {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 数据字典类型
     */
    private String dictType;

    /**
     * 字典键值
     */
    private String dictValue;

    /**
     * 字典键
     */
    private String dictKey;

    /**
     * 扩展值
     */
    private String extendValue;

    /**
     * 字典排序
     */
    private boolean seq;

    /**
     * 字典状态 1：正常 2：停止
     */
    private String status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
