package com.base.app.service.impl;

import com.base.app.constants.RequestLogConstants;
import com.base.app.dao.sys.RequestLogDAO;
import com.base.app.dto.log.RequestLogDTO;
import com.base.app.ro.log.ApiLogPageRo;
import com.base.app.service.RequestLogService;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    @Resource
    private RequestLogDAO requestLogDAO;

    @Override
    public Page<RequestLogDTO> searchPage(ApiLogPageRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = requestLogDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(requestLogDAO::selectPage, RequestLogDTO.class);
    }

    @Override
    public void delete(String timeKey) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (RequestLogConstants.TimeKey.WEEK.equals(timeKey)) {
            this.requestLogDAO.deleteTime(now.plusDays(-7).format(pattern), now.format(pattern));
        }
        if (RequestLogConstants.TimeKey.MONTH.equals(timeKey)) {
            this.requestLogDAO.deleteTime(now.plusMonths(-1).format(pattern), now.format(pattern));
        }
        if (RequestLogConstants.TimeKey.YEAR.equals(timeKey)) {
            this.requestLogDAO.deleteTime(now.plusYears(-1).format(pattern), now.format(pattern));
        }
    }
}
