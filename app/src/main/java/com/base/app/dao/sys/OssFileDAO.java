package com.base.app.dao.sys;

import com.base.app.po.sys.OssFilePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OssFileDAO extends BaseDAO<OssFilePo> {

    /**
     * 按MD5查询文件
     * @param fileMd5
     * @return
     */
    OssFilePo queryByMd5(String fileMd5);
}
