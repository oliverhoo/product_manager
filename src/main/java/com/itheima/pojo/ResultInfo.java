package com.itheima.pojo;

public class ResultInfo {
    //成功或失败
    private Boolean success;

    //提示信息
    private String message;

    //封装的数据
    private Object obj;

    public ResultInfo() {
    }

    public ResultInfo(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResultInfo(Boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
