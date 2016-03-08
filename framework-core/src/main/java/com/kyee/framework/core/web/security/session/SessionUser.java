package com.kyee.framework.core.web.security.session;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 程峰
 * 创建时间:15/9/8 下午1:34
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:session中标识一个登陆的bean
 */
public class SessionUser{
    /**
     * 此次登陆的id
     */
    private int id;
    /**
     * 此次登陆过期时间
     */
    private long expire;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 用于保存一些额外的属性
     */
    private HashMap<String,Object> properties=new HashMap<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public HashMap<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, Object> properties) {
        this.properties = properties;
    }
}
