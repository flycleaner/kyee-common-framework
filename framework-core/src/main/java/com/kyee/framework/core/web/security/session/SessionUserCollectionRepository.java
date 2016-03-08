package com.kyee.framework.core.web.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author 程峰
 * 创建时间:15/9/9 上午9:09
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明: 操作redis数据库 repository
 */

/**
 * 修改人：程峰
 * 修改时间：15/9/22 下午4:18
 * 修改原因: 将保存在redis中的变量全部变为大写，忽略大小写
 */
@Repository
public class SessionUserCollectionRepository {

    @Autowired
    private Environment environment;
    @Autowired
    private RedisTemplate<String,SessionUserCollection> redisTemplate;

    /**
     * 默认key前缀
     */
    private static final String DEFAULT_KEY_PREFIX = "SESSION:";
    /**
     * key前缀
     */
    private String keyPrefix;

    private ValueOperations<String ,SessionUserCollection> operations;

    @PostConstruct
    public void init(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(SessionUserCollection.class));
        operations = redisTemplate.opsForValue();
        String prefix;
        if((prefix = environment.getProperty("kyee.session.keyPrefix")) != null){
            keyPrefix = prefix;
        }else {
            keyPrefix = DEFAULT_KEY_PREFIX;
        }
    }

    /**
     * 向redis中插入数据
     * @param key 键值
     * @param sessionUserCollection value
     */
    public void set(String key,SessionUserCollection sessionUserCollection){
        operations.set(keyPrefix + key.toUpperCase(), sessionUserCollection);
    }

    /**
     * 向redis中获取数据
     * @param key 键值
     * @return value
     */
    public SessionUserCollection get(String key){
        return operations.get(keyPrefix+key.toUpperCase());
    }

    /**
     * 设置key的过期时间
     * @param key 键值
     * @param expire 过期时间 单位为秒
     */
    public void expire(String key,long expire){
        redisTemplate.expire(keyPrefix+key.toUpperCase(),expire, TimeUnit.SECONDS);
    }

    /**
     * 获取key的过期时间
     * @param key 键值
     * @return 过期时间 单位为秒
     */
    public long getExpire(String key){
        return redisTemplate.getExpire(keyPrefix+key.toUpperCase());
    }
}
