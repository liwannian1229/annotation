package com.lwn.response;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Deprecated
public class Paging<T> {

    private int pageIndex;

    private int pageSize;

    private int pageCount;

    private long totalCount;

    private Collection<T> data;

    public Paging() {
        this.data = new ArrayList<>();
    }

    /**
     * @param _pageIndex 页码
     * @param _pageSize  页容量
     */
    public Paging(int _pageIndex, int _pageSize) {
        this.pageIndex = _pageIndex;
        this.pageSize = _pageSize;
        this.data = new ArrayList<>();
    }

    /**
     * @param pageInfo 页信息
     */
    public Paging(PageInfo<T> pageInfo) {
        this.pageIndex = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.totalCount = pageInfo.getTotal();
        this.pageCount = pageInfo.getPages();
        if (pageInfo.getList() == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = pageInfo.getList();
        }
    }
}
