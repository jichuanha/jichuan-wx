package com.hzkans.crm.modules.trade.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author jc
 * @description
 * @create 2018/12/20
 */
public class QueryResult implements Serializable{

    private static final long serialVersionUID = 1L;

    //是否需要验证码 (1:需要 2:不需要)
    private Integer needCode = 2;
    private Integer drawNum;
    private String mobile;
    private List<Long> ids;


    public Integer getNeedCode() {
        return needCode;
    }

    public void setNeedCode(Integer needCode) {
        this.needCode = needCode;
    }

    public Integer getDrawNum() {
        return drawNum;
    }

    public void setDrawNum(Integer drawNum) {
        this.drawNum = drawNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
