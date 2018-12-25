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
    private Boolean codeFlg = false;
    private Integer drawNum;
    private String mobile;
    private List<Long> ids;
    private Long actId;
    private Integer actType;
    private String openId;


    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public Boolean getCodeFlg() {
        return codeFlg;
    }

    public void setCodeFlg(Boolean codeFlg) {
        this.codeFlg = codeFlg;
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
