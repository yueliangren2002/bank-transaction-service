package com.bank.exception;

/**
 * 业务异常
 *
 * @author
 * @date
 * @version: V1.0
 * @slogan:
 * @description: 业务异常
 **/
public class BusinessException extends Exception {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}
