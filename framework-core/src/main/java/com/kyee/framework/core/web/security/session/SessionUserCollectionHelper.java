package com.kyee.framework.core.web.security.session;

import com.kyee.framework.core.web.security.session.SessionUser;
import com.kyee.framework.core.web.security.session.SessionUserCollection;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author 程峰
 * 创建时间:15/9/8 下午10:15
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:处理sessionCollection的一些辅助函数
 */
public class SessionUserCollectionHelper {
    /**
     * 一个账号的SessionCollection
     */
    private SessionUserCollection sessionUserCollection;
    /**
     * 过期时间
     */
    private long expire;

    public SessionUserCollection getSessionUserCollection() {
        return sessionUserCollection;
    }

    public void setSessionUserCollection(SessionUserCollection sessionUserCollection) {
        this.sessionUserCollection = sessionUserCollection;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }


    /**
     * 只传入expire,则新建一个SessionCollection和第一个SessionUser
     * @param expire 过期时间
     */
    public SessionUserCollectionHelper(long expire){
        this.sessionUserCollection = new SessionUserCollection();
        sessionUserCollection.setTotal(0);
        this.expire = expire;
        addUser();
    }

    /**
     * 传入一个已有的sessionCollection
     * @param sessionUserCollection 一个账号的登陆集合
     */
    public SessionUserCollectionHelper(SessionUserCollection sessionUserCollection){
       this.sessionUserCollection = sessionUserCollection;
    }

    /**
     * 传入一个已有的sessionCollection和过期时间
     * @param sessionUserCollection 一个账号的登陆集合
     * @param expire 过期时间
     */
    public SessionUserCollectionHelper(SessionUserCollection sessionUserCollection,long expire){
        this.sessionUserCollection = sessionUserCollection;
        this.expire = expire;
    }

    /**
     * 获取当前未过期的登陆数，同时删除已过期的登陆
     * @return 当前未过期的登录数
     */
    public int getAliveSessions() {
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        if(users.size() == 0) {
            return 0;
        }
        Iterator<SessionUser> iterator = users.iterator();
        SessionUser user;
        while(iterator.hasNext()){
            user = iterator.next();
            if(isUserExpired(user)){
                iterator.remove();
                sessionUserCollection.decrementTotal();
            }
        }
        return sessionUserCollection.getTotal();
    }

    /**
     * 根据id获取当前账号登陆集的 登陆session
     * @param id 某个SessionUser的id
     * @return SessionUser
     */
    public SessionUser getUser(int id){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        for(SessionUser user:users){
            if(id == user.getId()){
                return user;
            }
        }
        return null;
    }

    /**
     * 返回前账号登陆集中最后一个登陆的sessionUser
     * @return SessionUser
     */
    public SessionUser getLastUser(){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        return users.getLast();
    }

    /**
     * 向当前账号的登陆集添加一个SessionUser
     */
    public void addUser(){
        SessionUser user = new SessionUser();
        user.setExpire(expire);
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        sessionUserCollection.incrementTotal();
        if(users.size()==0) {
            user.setId(sessionUserCollection.getTotal());
        }else{
            user.setId(users.getLast().getId()+1);
        }
        users.add(user);
    }

    /**
     * 删除当前账号的登陆集的第一个SessionUser
     */
    public void deleteFirstUser(){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        users.removeFirst();
        sessionUserCollection.decrementTotal();
    }

    /**
     * 删除当前账号的登陆集的最后一个SessionUser
     */
    public void deleteLastUser(){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        users.removeLast();
        sessionUserCollection.decrementTotal();
    }

    /**
     * 根据id删除 当前账号的登陆集中的SessionUser
     * @param id SessionUser id
     */
    public void deleteUser(int id){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        Iterator<SessionUser> iterator = users.iterator();
        SessionUser sessionUser;
        while (iterator.hasNext()){
            sessionUser = iterator.next();
            if(id == sessionUser.getId()){
                iterator.remove();
                sessionUserCollection.decrementTotal();
            }
        }
    }

    /**
     * 给当前账号的登陆集中的 SessionUser 添加属性
     * @param id SessionUser id
     * @param key 属性 key
     * @param object 属性 value
     * @return 是否添加成功，无此id返回false
     */
    public  boolean addProperty(int id,String key,Object object){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        Iterator<SessionUser> iterator = users.iterator();
        SessionUser sessionUser;
        while (iterator.hasNext()){
            sessionUser = iterator.next();
            if(id == sessionUser.getId()){
                sessionUser.getProperties().put(key, object);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前账号的登陆集中的SessionUser 的属性
     * @param id SessionUser id
     * @param key 属性 key
     * @return 属性 value
     */
    public Object getProperty(int id,String key){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        for(SessionUser user:users){
            if(id == user.getId()){
                return user.getProperties().get(key);
            }
        }
        return null;
    }

    /**
     * 某个用户是否过期
     * @param user sessionUser
     * @return 过期返回true,否则返回false
     */
    public boolean isUserExpired(SessionUser user){
        return (user.getExpire()*1000L+user.getCreateTime().getTime())<(new Date().getTime());
    }

    /**
     * 某个用户是否过期，(id,createTime) 才能唯一标示一个用户
     * @param id sessionUser id
     * @param createTime SessionUser 创建时间
     * @return 过期返回true,未过期返回false
     */
    public boolean isUserExpired(int id,Date createTime){
        LinkedList<SessionUser> users = sessionUserCollection.getUsers();
        for(SessionUser user:users){
            if(user.getId() == id && user.getCreateTime().getTime() == createTime.getTime()){
                return isUserExpired(user);
            }
        }
        return true;
    }

}
