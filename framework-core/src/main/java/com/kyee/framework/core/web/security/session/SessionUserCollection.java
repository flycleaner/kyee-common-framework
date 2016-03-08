package com.kyee.framework.core.web.security.session;

import java.util.LinkedList;

/**
 * @author 程峰
 * 创建时间:15/9/8 下午5:31
 * 任务号: MOBILEDEVELOP-10293
 * 创建说明: 一个账号下所有登陆数的collection
 */
public class SessionUserCollection {
    /**
     * 当前总共登陆数
     */
    private int total;
    /**
     * 该账号的所有登陆
     */
    private LinkedList<SessionUser> users = new LinkedList<>();


    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal(){
        return this.total;
    }

    public LinkedList<SessionUser> getUsers() {
        return users;
    }

    public void setUsers(LinkedList<SessionUser> users) {
        this.users = users;
    }

    /**
     * 增加该账号一个已登录数
     */
    public void incrementTotal(){
        this.total++;
    }

    /**
     * 减少一个该账号已登录数
     */
    public void decrementTotal(){
        this.total--;
    }
}
