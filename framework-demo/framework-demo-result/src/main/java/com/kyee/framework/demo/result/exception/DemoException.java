package com.kyee.framework.demo.result.exception;

import com.kyee.framework.core.exception.AbstractKyeeRuntimeException;
import com.kyee.framework.demo.result.state.DemoState;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午5:28 
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因 创建自定义异常示例
 */
public class DemoException extends AbstractKyeeRuntimeException {

    @Override
    public int getState() {
        return DemoState.E1;
    }

    public DemoException(String message) {
        super(message);
    }
}
