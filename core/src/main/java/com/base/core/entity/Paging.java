package com.base.core.entity;


import com.base.util.JsonUtils;
import com.base.util.ValidateHelper;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 分页参数
 */
@Data
public class Paging extends BaseValue {
    private static final long serialVersionUID = 6141741367303676093L;
    private Long page = 1L;
    private Long rows = 10L;
    private String sort;
    private Long total;
    private String footer;
    private String order;
    public String extraCond;
    public String extraOrder;
    public Map extra = new HashMap();
    private PagerVo pager = new PagerVo();

    public Paging() {
    }

    public <T> Page<T> search(Function<Paging, List> function, Class<T> tClass) {
        List apply = function.apply(this);
        if (ValidateHelper.isNotEmptyCollection(apply)) {
            if (apply.get(0).getClass() == tClass) {
                return Page.build(this).total(pager.getRowCount()).data(apply);
            }
        }else {
            apply = new ArrayList();
        }
        return Page.build(this).total(pager.getRowCount()).data(JsonUtils.createList(apply, tClass));
    }

    public PagerVo getPager() {
        this.pager.setPageId(this.page);
        this.pager.setPageSize(this.rows);
        String orderField = "";
        if (this.sort != null) {
            orderField = this.sort;
        }

        if (orderField != null && this.order != null) {
            orderField = orderField + " " + this.order;
        }

        this.pager.setOrderField(orderField);
        return this.pager;
    }

    public static Paging build() {
        return new Paging();
    }

    public static Paging build(Long page, Long pageSize) {
        Paging paging = build();
        paging.setPage(page);
        paging.setRows(pageSize);
        return paging;
    }

    public static Paging build(PageRo pageRo) {
        Paging paging = build();
        paging.setPage(pageRo.getCurrentPage());
        paging.setRows(pageRo.getPageSize());
        paging.setExtra(JsonUtils.bean2Map(pageRo));
        return paging;
    }

    public Paging extra(Map extra) {
        this.extra = extra;
        return this;
    }

    public void setRowCount(long rowCount) {
        this.getPager().setRowCount(rowCount);
        this.getPager().doPage();
    }
}
