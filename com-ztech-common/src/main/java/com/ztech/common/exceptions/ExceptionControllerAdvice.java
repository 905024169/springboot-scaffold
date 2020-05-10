package com.ztech.common.exceptions;

import com.ztech.exceptions.ValidException;
import com.ztech.msg.Constant;
import com.ztech.msg.MsgBody;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义抛出异常类
 *
 * @Author: www.java2345.com
 * @DATE: 2020/5/9 21:27
 **/
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /*@ExceptionHandler(MethodArgumentNotValidException.class)
    public String MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return objectError.getDefaultMessage();
    }*/

  /*  @ExceptionHandler(MethodArgumentNotValidException.class)
    public MsgBody MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 注意哦，这里返回类型是自定义响应体
        return new MsgBody(Constant.Error.getCode(), objectError.getDefaultMessage());
    }*/

    @ExceptionHandler(ValidException.class)
    public MsgBody ValidExceptionHandler(ValidException e) {
        return new MsgBody(Constant.Error.getCode(), e.getMessage());
    }
}
