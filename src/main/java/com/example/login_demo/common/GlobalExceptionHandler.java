package com.example.login_demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseData<Object> bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error(e.getErrorMsg());
        return ResponseData.Create(e);
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData<Object> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error(e.toString());
        return ResponseData.Create(new BizException(ResponseEnum.UNEXPECTED));
    }
}
