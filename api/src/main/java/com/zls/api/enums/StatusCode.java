package com.zls.api.enums;

public enum StatusCode {
    Success(200, "成功"),
    Fail(404, "失败"),
    InvalidParams(201, "非法的参数"),
    InvalidGrantType(202, "非法的授权类型");

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
