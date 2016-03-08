package com.kyee.framework.core.config;

/**
 * @author 程峰
 * 创建时间:15/9/11 上午11:29
 * 任务号:beanDefinitionBuilder
 * 创建说明:数据源属性
 */
public class DataSourceProperty {

    /**
     * datasource bean名字
     */
    private String dbName;
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
     * 驱动类名字
     */
    private String driverClassName;

    /**
     * 最大连接池数量
     */
    private int maxActive = -1;

    /**
     * 事务管理器名字
     */
    private String txManager;

    /**
     * mybatis扫描路径
     */
    private String basePackage;
    /**
     * flyway保存的sql文件位置
     */
    private String flywaySqlLocation;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

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

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

    public String getTxManager() {
        return txManager;
    }

    public void setTxManager(String txManager) {
        this.txManager = txManager;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getFlywaySqlLocation() {
        return flywaySqlLocation;
    }

    public void setFlywaySqlLocation(String flywaySqlLocation) {
        this.flywaySqlLocation = flywaySqlLocation;
    }
}
