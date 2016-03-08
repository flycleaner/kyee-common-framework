package com.kyee.framework.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author 梁志艳
 * 创建时间：2015-09-08 上午11:52
 * 任务号：MOBILEDEVELOP-10307
 * 创建原因：单数据源配置属性类
 */
@ConfigurationProperties(prefix = "kyee.singledb")
public class SingleDbProperties {
    /**
     * 数据库连接url
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 最大连接池数量
     */
    private int maxActive = -1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }
}
