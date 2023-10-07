package com.base.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 分页对象
 */
@Data
public class Page<T> extends BaseValue{

    /**
     * 分页数据集合
     */
    private List<T> dataList;

    /**
     * 总数
     */
    private Long total = 0L;

    /**
     * 当前页
     */
    private Long currentPage;

    /**
     * 分页条数
     */
    private Long pageSize;

    public static Page build(){
        return new Page<>();
    }

    public static Page build(Long page,Long pageSize){
        Page p = build();
        p.setCurrentPage(page);
        p.setPageSize(pageSize);
        return p;
    }
    public static Page build(Paging paging){
        Page p = build();
        p.setCurrentPage(paging.getPage());
        p.setPageSize(paging.getRows());
        return p;
    }

    public Page<T> data(List<T> dataList) {
        this.dataList = dataList;
        return this;
    }

    public Page<T> total(Long total) {
        this.total = total;
        return this;
    }

    public Page<T> page(Long page){
        this.currentPage = page;
        return this;
    }

    public Page<T> pageSize(Long pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
