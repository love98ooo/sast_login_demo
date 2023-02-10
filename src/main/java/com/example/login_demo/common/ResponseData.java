package com.example.login_demo.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseData<T> {

    private T data;
    private final boolean success;
    private final Integer errCode;
    private String errMsg;

    public ResponseData(boolean success, Integer errCode) {
        this.success = success;
        this.errCode = errCode;
    }

    public ResponseData(T data, boolean success, Integer errCode, String errMsg) {
        this.data = data;
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseData(T data, boolean success, Integer errCode) {
        this.data = data;
        this.success = success;
        this.errCode = errCode;
    }

    public ResponseData(boolean success, Integer errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.errCode == ResponseEnum.SUCCESS.getStatus();
    }

    public int getErrCode() {
        return errCode;
    }

    public T getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public static <T> ResponseData<T> Create(ResponseEnum e) {
        return new ResponseData<>(false, e.getStatus(), e.getMsg());
    }

    public static <T> ResponseData<T> Create(BizException e) {
        return new ResponseData<>(false, e.getErrorCode(), e.getErrorMsg());
    }

    public static <T> ResponseData<T> Success() {
        return new ResponseData<>(true, ResponseEnum.SUCCESS.getStatus());
    }

    public static <T> ResponseData<T> Success(String msg) {
        return new ResponseData<>(true, ResponseEnum.SUCCESS.getStatus(), msg);
    }

    public static <T> ResponseData<T> Success(T data) {
        return new ResponseData<>(data, true, ResponseEnum.SUCCESS.getStatus());
    }

    public static <T> ResponseData<T> Success(String msg, T data) {
        return new ResponseData<>(data, true, ResponseEnum.SUCCESS.getStatus(), msg);
    }


    public static <T> ResponseData<T> Error() {
        return new ResponseData<>(false, ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMsg());
    }


    public static <T> ResponseData<T> Error(String errorMessage) {
        return new ResponseData<>(false, ResponseEnum.ERROR.getStatus(), errorMessage);
    }

    public static <T> ResponseData<T> Error(int errorCode, String errorMessage) {
        return new ResponseData<>(false, errorCode, errorMessage);
    }
}
