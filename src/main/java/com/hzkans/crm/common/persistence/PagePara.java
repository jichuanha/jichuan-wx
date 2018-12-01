package com.hzkans.crm.common.persistence;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/1
 */
public class PagePara<T> {

    private Integer currentPage;
    private Integer pageSize;
    private Integer count;
    private T data;
    private List<T> list = new ArrayList<>();


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
