package com.base.app.service.impl;

import com.base.app.constants.RequestLogConstants;
import com.base.app.dao.sys.SqlLogDAO;
import com.base.app.dto.log.SqlLogDTO;
import com.base.app.ro.log.SQLLogPageRo;
import com.base.app.service.SQLLogService;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.util.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SQLLogServiceImpl implements SQLLogService {

    @Resource
    private SqlLogDAO sqlLogDAO;

    @Override
    public Page<SqlLogDTO> searchPage(SQLLogPageRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = sqlLogDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(sqlLogDAO::selectPage, SqlLogDTO.class);
    }

    @Override
    public SqlLogDTO selectById(Long sqlId) {
        return JsonUtils.createBean(this.sqlLogDAO.queryById(sqlId), SqlLogDTO.class);
    }

    @Override
    public void delete(String key) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (RequestLogConstants.TimeKey.WEEK.equals(key)) {
            this.sqlLogDAO.deleteTime(now.plusDays(-7).format(pattern), now.format(pattern));
        }
        if (RequestLogConstants.TimeKey.MONTH.equals(key)) {
            this.sqlLogDAO.deleteTime(now.plusMonths(-1).format(pattern), now.format(pattern));
        }
        if (RequestLogConstants.TimeKey.YEAR.equals(key)) {
            this.sqlLogDAO.deleteTime(now.plusYears(-1).format(pattern), now.format(pattern));
        }
    }
}
