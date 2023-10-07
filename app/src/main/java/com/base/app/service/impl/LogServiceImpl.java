package com.base.app.service.impl;

import com.base.app.dao.sys.RequestLogDAO;
import com.base.app.dao.sys.SqlLogDAO;
import com.base.app.po.sys.RequestLogPo;
import com.base.app.po.sys.SqlLogPo;
import com.base.core.entity.dto.RequestLog;
import com.base.core.entity.dto.SqlLogDTO;
import com.base.core.service.LogService;
import com.base.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private RequestLogDAO requestLogDAO;

    @Resource
    private SqlLogDAO sqlLogDAO;

    @Override
    public void saveLog(RequestLog requestLog) {
        RequestLogPo logPo = JsonUtils.createBean(requestLog, RequestLogPo.class);
        logPo.setYear(LocalDate.now().getYear());
        int monthValue = LocalDate.now().getMonthValue();
        String yearOfMonth = logPo.getYear() + "";
        if (monthValue < 10) {
            yearOfMonth = yearOfMonth + "0" + monthValue;
        } else {
            yearOfMonth = yearOfMonth + monthValue;
        }
        logPo.setYearOfMonth(Integer.valueOf(yearOfMonth));
        logPo.setGmtCreate(new Date());
        this.requestLogDAO.add(logPo);
    }

    @Override
    public void saveSQL(SqlLogDTO sqlLog) {
        SqlLogPo logPo = JsonUtils.createBean(sqlLog, SqlLogPo.class);
        logPo.setYear(LocalDate.now().getYear());
        int monthValue = LocalDate.now().getMonthValue();
        String yearOfMonth = logPo.getYear() + "";
        if (monthValue < 10) {
            yearOfMonth = yearOfMonth + "0" + monthValue;
        } else {
            yearOfMonth = yearOfMonth + monthValue;
        }
        logPo.setYearOfMonth(Integer.valueOf(yearOfMonth));
        this.sqlLogDAO.add(logPo);
    }
}
