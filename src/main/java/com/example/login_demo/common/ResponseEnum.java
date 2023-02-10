package com.example.login_demo.common;


public enum ResponseEnum {

    SUCCESS(0,"SUCCESS"),
    ERROR(1001,"ERROR"),
    ILLEGAL_ARGUMENT(1002,"ILLEGAL_ARGUMENT"),
    USER_NOT_LOGIN(1003, "USER_NOT_LOGIN"),
    USER_ROLE_LIMITED(1004, "USER_ROLE_LIMITED"),
    OUT_OF_STOCK(1005, "OUT_OF_STOCK"),
    INVALID_CACHE_KEY(1006, "INVALID_CACHE_KEY"),
    CREATE_USER_FAIL(1007, "CREATE_USER_FAIL"),
    UPDATE_USER_FAIL(1008, "UPDATE_USER_FAIL"),
    NOT_USER(1009, "NOT_USER"),
    UNEXPECTED(-1, "UNEXPECTED");

    private final Integer status;
    private final String msg;


    ResponseEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public int getStatus(){
        return status;
    }
    public String getMsg(){
        return msg;
    }

}
