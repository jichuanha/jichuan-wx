package com.hzkans.crm.modules.sys.entity;

import java.util.Date;
/**
 * Created with IDEA
 * author:dengtm
 * Date:2018/11/23
 * Time:18:22
 */
public class ChangePasswordDO {
    private Integer id;
    private String userId;
    private Date registerDate;
    private String validataCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getValidataCode() {
        return validataCode;
    }

    public void setValidataCode(String validataCode) {
        this.validataCode = validataCode;
    }
}
