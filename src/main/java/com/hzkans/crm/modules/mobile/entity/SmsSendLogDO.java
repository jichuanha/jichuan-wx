package com.hzkans.crm.modules.mobile.entity;


import java.io.Serializable;

/**
 * Created by lizg on 2017/1/11.
 */
public class SmsSendLogDO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;

    private String mobile;

    private String remark;

    private Integer status;

    private String sendResult;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSendResult() {
        return sendResult;
    }

    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }
}
