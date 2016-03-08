package com.kyee.framework.core.exception;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午5:24
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因：自动处理异常的异常抽象基类
 */
public abstract class AbstractKyeeRuntimeException extends RuntimeException{
    /**
     * 所有子类都需要实现状态获取的接口
     * @return 异常状态码，约定4开头
     */
    public abstract int getState();

    /**
     * 提供message的构造函数
     * @param message 提供的message
     */
    public AbstractKyeeRuntimeException(String message) {
        super(message);
    }

    /**
     * 提供消息和异常的构造函数
     * @param message 提供的message
     * @param cause 触发的异常
     */
    public AbstractKyeeRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
