package com.kyee.framework.core.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author 程峰
 * 创建时间:15/9/8 下午4:19
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:配置redis
 */
@Configuration
public class RedisConfig {

    @Autowired
    RedisProperties redisProperties;

    /**
     * jedis连接池配置
     * @return jedis连接池配置
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisProperties.Pool pool = redisProperties.getPool();
        jedisPoolConfig.setMaxIdle(pool.getMaxIdle());
        jedisPoolConfig.setMinIdle(pool.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(pool.getMaxWait());
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     * @param jedisPoolConfig jedis连接池配置
     * @return jedis连接工厂
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.setHostName(redisProperties.getHost());
        jedisConnectionFactory.setPassword(redisProperties.getPassword());
        jedisConnectionFactory.setPort(redisProperties.getPort());
        jedisConnectionFactory.setUsePool(true);
        return jedisConnectionFactory;
    }

    /**
     * redisTemplate,类似于jdbcTemplate
     * @param jedisConnectionFactory jedis连接工厂
     * @return redisTemplate
     */
    @Bean
    @SuppressWarnings("unchecked")
    RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

}
