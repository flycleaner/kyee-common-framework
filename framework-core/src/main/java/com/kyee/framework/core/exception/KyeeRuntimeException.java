package com.kyee.framework.core.exception;

import com.kyee.framework.core.web.result.ResultState;

/**
 * @author 梁志艳
 * 创建时间：2015-07-22 上午10:56 
 * 任务号：MOBILEDEVELOP-9620
 * 创建原因：项目异常基类的一个实现类
 */
public class KyeeRuntimeException extends AbstractKyeeRuntimeException {
    /**
     * 默认是5
     * @return 返回的状态信息
     */
    @Override
    public int getState() {
        return ResultState.KNOWN_ERROR;
    }

    /**
     * 通过消息构造这个异常
     * @param message 消息
     */
    public KyeeRuntimeException(String message) {
        super(message);
    }

    /**
     * 通过消息和触发的异常构造
     * @param message 消息
     * @param cause 触发异常
     */
    public KyeeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
