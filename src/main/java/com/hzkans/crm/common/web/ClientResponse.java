package com.hzkans.crm.common.web;

/**
 * Created by duke on 15/9/7.
 */
public class ClientResponse<T> {
    private T module;
    private long totalCount;

    public T getModule() {
        return module;
    }

    public void setModule(T module) {
        this.module = module;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
