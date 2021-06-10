package com.musichive.common.bean.home;

import java.util.List;

public class MusicLabelResponseBean {
    private boolean status;

    private String msg;

    private String code;

    private List<UserLabe> data;

    private List<UserLabe> orderType;

    private List<UserLabe> dtype;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<UserLabe> getData() {
        return data;
    }

    public void setData(List<UserLabe> data) {
        this.data = data;
    }

    public List<UserLabe> getOrderType() {
        return orderType;
    }

    public void setOrderType(List<UserLabe> orderType) {
        this.orderType = orderType;
    }

    public List<UserLabe> getDtype() {
        return dtype;
    }

    public void setDtype(List<UserLabe> dtype) {
        this.dtype = dtype;
    }
}
