package com.example.login_demo.common;

public class ResponseData<T> {

    private T data;
    private Boolean success;
    private Integer errCode;
    private String errMsg;

    public ResponseData(T data, Boolean success, Integer errCode, String errMsg) {
        this.data = data;
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ResponseData(Boolean success, Integer errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public T getData() {
        return data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public static <T> ResponseData<T> Create(BizException e) {
        return new ResponseData<>(false, e.getErrorCode(), e.getErrorMsg());
    }


    public static <T> ResponseData<T> Success(T data) {
        return new ResponseData<>(data, true, ResponseEnum.SUCCESS.getStatus(), null);
    }


    public static <T> ResponseData<T> Error() {
        return new ResponseData<>(false, ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMsg());
    }
    public static <T> ResponseData<T> Error(String errMsg) {
        return new ResponseData<>(false, ResponseEnum.ERROR.getStatus(), errMsg);
    }
}
