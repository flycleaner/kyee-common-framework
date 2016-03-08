package com.kyee.framework.core.web.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:32
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明: 管理session服务
 */

/**
 * 修改人：程峰
 * 修改时间：15/9/22 下午4:17
 * 修改原因: sessionUserCollection可能为null，会导致空指针异常
 */
@Service
public class SessionManager implements ISessionManager{

    @Autowired
    private SessionPolicy sessionPolicy;
    @Autowired
    private SessionUserCollectionRepository repository;


    /**
     * 新建session
     * @param username 用户名
     * @param expire 该session存活时间，单位为秒
     * */
    @Override
    public SessionUser createSessionUser(String username, long expire) {
        boolean flag = true;
        SessionUserCollection sessionUserCollection = repository.get(username);
        SessionUserCollectionHelper sessionUserCollectionHelper;
        if(sessionUserCollection == null){
            sessionUserCollectionHelper= new SessionUserCollectionHelper(expire);
            repository.set(username,sessionUserCollectionHelper.getSessionUserCollection());
            repository.expire(username,expire);
        }else{
            sessionUserCollectionHelper = new SessionUserCollectionHelper(sessionUserCollection,expire);
            if(sessionUserCollectionHelper.getAliveSessions()< sessionPolicy.getSessionCount()){//已经登录数小于最大session数
                sessionUserCollectionHelper.addUser();
                repository.set(username,sessionUserCollectionHelper.getSessionUserCollection());
            }else{  //超过最大登陆数，根据策略移除第一个或者不允许登陆
                switch (sessionPolicy.getSessionControl()){
                    case FormerPrior:    //先来的优先，不允许最后一个登陆
                        flag=false;
                        break;
                    case LatterPrior:   //后来的优先，插入这一个，删除第一个
                        sessionUserCollectionHelper.deleteFirstUser();
                        sessionUserCollectionHelper.addUser();
                        repository.set(username,sessionUserCollection);
                        break;
                    default:
                }
            }
        }
        if(flag){//登陆成功
            if(repository.getExpire(username)<expire){  //则将key: username 的 expire 设为所有登陆的最大的expire
                repository.expire(username, expire);
            }
            return sessionUserCollectionHelper.getLastUser();   //返回最后一个登陆
        }
        return null;
    }

    /**
     * 用于保存用户的一些其它的属性
     * @param username 用户名
     * @param key 字段的key值
     * @param content 字段内容
     * @return 保存结果，成功返回true，否则false
     */
    @Override
    public boolean setValue(String username, int id, String key, Object content) {
        SessionUserCollection sessionUserCollection = repository.get(username);
        if(sessionUserCollection != null){
            SessionUserCollectionHelper sessionUserCollectionHelper = new SessionUserCollectionHelper(sessionUserCollection);
            sessionUserCollectionHelper.addProperty(id, key, content);
            repository.set(username,sessionUserCollectionHelper.getSessionUserCollection());
            return true;
        }
        return false;
    }

    /**
     * 返回保存的用户数据
     *
     * @param username 用户名
     * @param id 获取登陆id 的数据
     * @param key 字段的key值
     * @return key对应的字段
     */
    @Override
    public Object getValue(String username, int id, String key) {
        SessionUserCollection sessionUserCollection = repository.get(username);
        if(sessionUserCollection == null){
            return null;
        }
        SessionUserCollectionHelper helper = new SessionUserCollectionHelper(sessionUserCollection);
        return helper.getProperty(id,key);
    }

    /**
     * 删除session
     * @param  username 用户名
     * @param id 登陆id
     * */
    @Override
    public void destroySessionUser(String username, int id) {
        SessionUserCollection sessionUserCollection = repository.get(username);
        if(sessionUserCollection == null){
            return;
        }
        SessionUserCollectionHelper sessionUserCollectionHelper = new SessionUserCollectionHelper(sessionUserCollection);
        sessionUserCollectionHelper.deleteUser(id);
        repository.set(username,sessionUserCollectionHelper.getSessionUserCollection());
    }

    /**
     * 判断用户的一个登陆是否是否有效
     * @param username 用户名
     * @param id 登陆id
     * @param createTime 登陆创建时间
     * @return 有效返回true,无效返回false
     * */
    @Override
    public boolean isSessionUserValid(String username, int id,Date createTime) {
        SessionUserCollection sessionUserCollection = repository.get(username);
        if(sessionUserCollection == null){
            return false;
        }
        SessionUserCollectionHelper sessionUserCollectionHelper = new SessionUserCollectionHelper(sessionUserCollection);
        return !sessionUserCollectionHelper.isUserExpired(id,createTime);
    }


}
