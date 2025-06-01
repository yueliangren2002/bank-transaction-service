package com.bank.entity.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 通用返回对象
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    protected CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param
     */
    public static <T> CommonResult<T> success() {
        return new CommonResult<T>(HttpStatus.OK.value(), null, null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(HttpStatus.OK.value(), null, data);
    }

    /**
     * 成功返回结果
     *
     * @param data    获取的数据
     * @param message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(HttpStatus.OK.value(), message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(long code, String message) {
        return new CommonResult<T>(code, message, null);
    }

    public static <T> CommonResult<T> failed(T data, String message) {
        return new CommonResult<T>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> CommonResult<T> failed() {
        return failed(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
    }

}
