package com.kyee.framework.core.web.result;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author 梁志艳
 * 创建时间：2015-07-22 上午9:48 
 * 任务号：MOBILEDEVELOP-9620
 * 创建原因：返回结果的封装
 */
public class Result {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 返回结果
     */
    private Object value;
    /**
     * 状态
     * 约定：成功状态2开头，失败状态3开头，异常状态4开头
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int state = -1;
    /**
     * 消息
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
