package com.kyee.framework.core.exception;

import com.kyee.framework.core.web.result.Result;
import com.kyee.framework.core.web.result.ResultHelper;
import com.kyee.framework.core.web.result.ResultState;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 梁志艳
 * 创建时间：2015-07-22 上午10:59 
 * 任务号：MOBILEDEVELOP-9620
 * 创建原因：全局异常处理类
 */
@ControllerAdvice
public class KyeeExceptionHandler {
    /**
     * 对所有自定义的异常做统一处理
     * @param request http请求
     * @param ex 发生的异常
     * @return 统一的返回结构
     */
    @ResponseBody
    @ExceptionHandler(AbstractKyeeRuntimeException.class)
    public Result handleKyeeException(HttpServletRequest request,AbstractKyeeRuntimeException ex){
        return ResultHelper.failureResult(String.format("[known error] %s: %s", ex.getClass().getSimpleName(),ex.getMessage()), ex.getState());
    }

    /**
     * 对所有三方的异常的处理
     * @param request http请求
     * @param ex 发生的异常
     * @return 统一的返回结构
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleUnknownException(HttpServletRequest request,Throwable ex){
        return ResultHelper.failureResult(String.format("[unknown error] %s: %s", ex.getClass().getSimpleName(), ex.getMessage()), ResultState.UNKNOWN_ERROR);
    }
}
