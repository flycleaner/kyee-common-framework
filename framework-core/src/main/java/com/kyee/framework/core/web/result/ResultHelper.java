package com.kyee.framework.core.web.result;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午4:20 
 * 任务号：MOBILEDEVELOP-9620
 * 创建原因：返回结果的工具类，可以快速返回结果
 * @see Result
 */
public class ResultHelper {
    /**
     * 创建失败的返回
     * @param message 失败的信息
     * @param state 失败的状态，应该3开头
     * @return 固定的返回结果
     */
    public static Result failureResult(String message, int state) {
        Result result = new Result();
        result.setSuccess(false);
        result.setValue(null);
        result.setMessage(message);
        result.setState(state);
        return result;
    }

    /**
     * 成功的返回
     * @param value 返回的value
     * @return 成功状态的Builder
     */
    public static SuccessResultBuilder successResult(Object value) {
        return new ResultHelper.SuccessResultBuilder(value);
    }

    /**
     * 成功返回结果的Builder，使用了Builder Pattern
     */
    public static class SuccessResultBuilder {
        /**
         * 固定的返回结果
         */
        private Result result = new Result();

        /**
         * 根据value构建
         * @param value 需要设置的Result的value值
         */
        SuccessResultBuilder(Object value) {
            this.result.setSuccess(true);
            this.result.setValue(value);
        }

        /**
         * 设置message
         * @param message 消息
         * @return 返回构造器自己，便于链式调用
         */
        public SuccessResultBuilder message(String message) {
            this.result.setMessage(message);
            return this;
        }

        /**
         * 设置state
         * @param state 状态
         * @return 返回构造器自己，便于链式调用
         */
        public SuccessResultBuilder state(int state) {
            this.result.setState(state);
            return this;
        }

        /**
         * 获取Result
         * @return 获取的固定返回Result
         */
        public Result build() {
            return this.result;
        }
    }
}
