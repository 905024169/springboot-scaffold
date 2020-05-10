package com.ztech.exceptions;

/**
 * 自定义Valid异常类，用于在service判断Valid验证
 * @Author: www.java2345.com
 * @DATE: 2020/5/10 19:58
 **/
public class ValidException  extends RuntimeException{
    public ValidException() {
        super();
    }

    public ValidException(String message) {
        super(message);
    }

}
