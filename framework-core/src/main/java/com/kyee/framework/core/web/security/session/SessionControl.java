package com.kyee.framework.core.web.security.session;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:18
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:session控制策略
 */
public enum  SessionControl {
    /**
     * FormerPrior: 先来的优先，即如果某用户登陆设备数大于maxSession的时候，后来者不能登陆
     * LatterPrior: 后来的优先，即如果某用户登陆设备数大于maxSession的时候，第一个登陆的被挤出
     */
    FormerPrior(0),LatterPrior(1);
    private final int value;

    SessionControl(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
