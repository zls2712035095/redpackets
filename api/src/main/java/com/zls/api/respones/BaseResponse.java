package com.zls.api.respones;

import com.zls.api.enums.StatusCode;
import lombok.Data;

@Data
public class BaseResponse <T>{
    private int code;
    private String msg;

    private T data;

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }
    public BaseResponse(Integer code ,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

}
