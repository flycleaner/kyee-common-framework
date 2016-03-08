package com.kyee.framework.core.web.result;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午4:48 
 * 任务号：MOBILEDEVELOP-9620
 * 创建原因：框架中用到的默认状态值
 */
public class ResultState {
    /**
     * {@link com.kyee.framework.core.exception.KyeeRuntimeException}的state
     */
    public static final int KNOWN_ERROR=4;
    /**
     * 除了继承子{@link com.kyee.framework.core.exception.AbstractKyeeRuntimeException}的异常的state
     */
    public static final int UNKNOWN_ERROR=5;
}
