package com.kyee.framework.mutidb.jta.domain;

/**
 * @author 程峰
 * 创建时间:15/9/1 下午2:53
 * 任务号:MOBILEDEVELOP-10187
 * 创建说明:数据源属性
 */
public class DataSourceProperty {
    /**
     * 数据源名字，必须是独特的
     */
    private String uniqueResourceName;
    /**
     * 数据源类型: mysql,oracle,sqlServer
     */
    private String dataSourceType;
    /**
     * 数据源url
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
     * 连接池大小
     */
    private int poolSize = 10;
    /**
     * 获取连接失败重新获等待最大时间
     */
    private int borrowConnectionTimeout = 20;
    /**
     * 最大获取数据时间
     */
    private int reapTimeout = 0;
    /**
     * 最大闲置时间，超过最小连接池连接的连接将将关闭
     */
    private int maxIdleTime=60;
    /**
     * 连接回收时间
     */
    private int maintenanceInterval=60;
    /**
     * java数据库连接池，最大可等待获取datasouce的时间
     */
    private int loginTimeout = 60;
    /**
     * 最小连接池个数
     */
    private int minPoolSize=20;
    /**
     * 最大连接池个数
     */
    private int maxPoolSize=50;
    /**
     * 连接最大存活时间，超过这个且没有正在使用的连接将自动销毁,0无限制
     */
    private int maxLifetime = 0;
    /**
     * 测试连接是否可用
     */
    private String testQuery;
    /**
     * 该数据源的mybatis扫描路径
     */
    private String basePackage;

    private String flywaySqlLocation;

    public String getUniqueResourceName() {
        return uniqueResourceName;
    }

    public void setUniqueResourceName(String uniqueResourceName) {
        this.uniqueResourceName = uniqueResourceName;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
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

    public int getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    public int getBorrowConnectionTimeout() {
        return borrowConnectionTimeout;
    }

    public void setBorrowConnectionTimeout(int borrowConnectionTimeout) {
        this.borrowConnectionTimeout = borrowConnectionTimeout;
    }

    public int getReapTimeout() {
        return reapTimeout;
    }

    public void setReapTimeout(int reapTimeout) {
        this.reapTimeout = reapTimeout;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getMaintenanceInterval() {
        return maintenanceInterval;
    }

    public void setMaintenanceInterval(int maintenanceInterval) {
        this.maintenanceInterval = maintenanceInterval;
    }

    public int getLoginTimeout() {
        return loginTimeout;
    }

    public void setLoginTimeout(int loginTimeout) {
        this.loginTimeout = loginTimeout;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public int getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getTestQuery() {
        return testQuery;
    }

    public void setTestQuery(String testQuery) {
        this.testQuery = testQuery;
    }

    public int getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(int maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getFlywaySqlLocation() {
        return flywaySqlLocation;
    }

    public void setFlywaySqlLocation(String flywaySqlLocation) {
        this.flywaySqlLocation = flywaySqlLocation;
    }
}
