package com.base.core.mybatis;


import com.base.core.entity.Paging;

import java.util.List;

/**
 * DAO 基类
 * @param <T>
 */
public interface BaseDAO<T> {

    /**
     * 新增数据
     */
    void add(T t);

    /**
     * 更新数据
     */
    void update(T t);

    /**
     * 根据主键删除数据
     */
    void delete(Object id);

    /**
     * 分页查询-获取总数
     */
    Long selectCount(Paging paging);

    /**
     * 列表查询
     * @return
     */
    List<T> selectNoPage(Paging paging);

    /**
     * 分页查询-获取列表
     */
    List<T> selectPage(Paging paging);

    /**
     * 根据主键获取数据
     * @return
     */
    T queryById(Object id);

    /**
     * 批量新增数据
     */
    void addList(List<T> list);

    /**
     * 批量修改数据
     */
    void updateList(List<T> list);
}
