package com.tenerdy.mobiledomain.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Pager<T> {
    private Integer page = 1;


    private Integer pageSize = 5;


    private Object condition;

    private Integer total = 0;

    @JsonProperty("rows")
    private List<T> data;

    public Pager() {
        super();
    }

    public Pager(Integer page, Integer pageSize, T condition) {
        super();
        setPage(page);
        setPagepageSize(pageSize);
        this.condition = condition;
    }

    public Pager(Integer page, Integer pageSize) {
        super();
        setPage(page);
        setPagepageSize(pageSize);
    }

    public Pager(T condition) {
        super();
        this.condition = condition;
    }

    public Pager(Integer total) {
        super();
        setTotal(total);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        // 页码不能小于1
        if (page != null && page >= 1) {
            this.page = page;
        }
    }

    public Integer getPagepageSize() {
        return pageSize;
    }

    public void setPagepageSize(Integer pagepageSize) {
        // 分页大小必须大于等于1
        if (pagepageSize != null && pagepageSize >= 1) {
            this.pageSize = pagepageSize;
        }
    }

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        // 总记录数不能小于0
        if (total != null && total >= 0) {
            this.total = total;
        }
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getPages() {
        int pages = 0;
        if (pageSize >= 0) {
            // 计算总页数：需要判断能否分层整页，不能分成整页需要加多1页
            pages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        }
        return pages;
    }

    public Integer getPrePage() {
        if (page > 0) {
            int prepage = page - 1 <= 0 ? 1 : page - 1;
            return prepage;
        }
        // 上一页为0时，表示没有任何数据
        return 0;
    }


    public Integer getNextPage() {
        int pages = getPages();
        if (pages > 0) {
            int nextpage = page + 1 <= pages ? page + 1 : pages;
            return nextpage;
        }
        // 当没有数据时，下一页就是0页
        return 0;
    }

    public Integer getOffset() {
        if (page >= 1) {
            return (page - 1) * pageSize;
        }
        // 没有分页查询或没有数据,返回0
        return 0;
    }

    public Integer getEnd() {
        if (page >= 1) {
            return page * pageSize;
        }
        // 没有分页查询或没有数据,返回0
        return 0;
    }

}
