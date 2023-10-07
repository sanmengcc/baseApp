package com.base.core.entity;

/**
 * 分页计算器
 */
public class PagerVo extends BaseValue {
    private static final long serialVersionUID = 8854823747011229592L;
    private Long pageId = 1L;
    private Long rowCount = 0L;
    private Long pageSize = 10L;
    private Long pageCount = 0L;
    private Long pageOffset = 0L;
    private Long pageTail = 0L;
    private String orderField;
    private boolean orderDirection = true;

    public PagerVo() {
    }

    protected void doPage() {
        this.pageCount =  this.rowCount / this.pageSize + 1;
        if (this.rowCount % (long)this.pageSize == 0L && this.pageCount > 1) {
            --this.pageCount;
        }

        this.pageOffset = (this.pageId - 1) * this.pageSize;
        this.pageTail = this.pageOffset + this.pageSize;
        if ((long)(this.pageOffset + this.pageSize) > this.rowCount) {
            this.pageTail = this.rowCount;
        }

    }

    public String getOrderCondition() {
        String condition = "";
        if (this.orderField != null && this.orderField.length() != 0) {
            condition = " order by " + this.orderField + (this.orderDirection ? " " : " desc ");
        }

        return condition;
    }

    public String getMysqlQueryCondition() {
        String condition = "";
        condition = " limit " + this.pageOffset + "," + this.pageSize;
        return condition;
    }

    public void setOrderDirection(boolean orderDirection) {
        this.orderDirection = orderDirection;
    }

    public boolean isOrderDirection() {
        return this.orderDirection;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderField() {
        return this.orderField;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public long getPageCount() {
        return this.pageCount;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public long getPageId() {
        return this.pageId;
    }

    public void setPageOffset(Long pageOffset) {
        this.pageOffset = pageOffset;
    }

    public long getPageOffset() {
        return this.pageOffset;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageSize() {
        return this.pageSize;
    }

    public void setPageTail(Long pageTail) {
        this.pageTail = pageTail;
    }

    public long getPageTail() {
        return this.pageTail;
    }

    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
        this.doPage();
    }

    public long getRowCount() {
        return this.rowCount;
    }
}
