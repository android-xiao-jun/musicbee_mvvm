package com.musichive.common.bean;


import java.io.Serializable;

public class BaseResponseBean<T> implements Serializable{

    private boolean status;

    private String msg;

    private String message;

    private String code;

    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return ResponseCode.isRequestSuccess(code);
    }

    public boolean isInValidToken(){
        return ResponseCode.isInValidToken(code);
    }

    public boolean isLegal(){
        return data != null;
    }

    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
