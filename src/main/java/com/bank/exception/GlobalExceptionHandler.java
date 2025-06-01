package com.bank.exception;

import com.bank.entity.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理，避免异常敏感信息直接暴露给客户端
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常捕捉
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public CommonResult<String> ExepitonHandler(Exception e) {
        log.error("catch globalExceptionHandler,e", e);
        return CommonResult.failed("请求错误:->" + e.getMessage());
    }

}
