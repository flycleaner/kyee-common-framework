package com.kyee.framework.core.web.security.session;

import java.util.Date;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:06
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:管理session服务接口
 */
public interface ISessionManager {

    /**
     * 新建session
     * @param username 用户名
     * @param expire 该session存活时间，单位为秒
     * */
    SessionUser createSessionUser(String username, long expire);

    /**
     * 用于保存用户的一些其它的属性
     * @param username 用户名
     * @param key 字段的key值
     * @param content 字段内容
     * @return 保存结果，成功返回true，否则false
     */
    boolean setValue(String username,int id,String key,Object content);

    /**
     * 返回保存的用户数据
     *
     * @param username 用户名
     * @param id 获取登陆id 的数据
     * @param key 字段的key值
     * @return key对应的字段
     */
    Object getValue(String username,int id,String key);

    /**
     * 删除session
     * @param  username 用户名
     * @param id 登陆id
     * */
    void destroySessionUser(String username,int id);

    /**
     * 判断用户的一个登陆是否是否有效
     * @param username 用户名
     * @param id 登陆id
     * @param createTime 登陆创建时间
     * @return 有效返回true,无效返回false
     * */
    boolean isSessionUserValid(String username,int id,Date createTime);

}
