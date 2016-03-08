package com.kyee.framework.core.data.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程峰
 * 创建时间:15/9/8 下午4:20
 * 任务号: MOBILEDEVELOP-10293
 * 创建说明: redis的属性
 */
@Configuration
@ConfigurationProperties(prefix = "kyee.redis")
public class RedisProperties {
    /**
     *主机名
     */
    private String host="localhost";
    /**
     * 密码
     */
    private String password;
    /**
     * 端口
     */
    private int port=6379;
    /**
     * 连接池
     */
    private Pool pool;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public static class Pool{
        /**
         * 最大空闲时间
         */
        private int maxIdle=100;
        /**
         * 最小空闲时间
         */
        private int minIdle=10;
        /**
         * 最大等待时间
         */
        private int maxWait=1000;

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }
    }
}
