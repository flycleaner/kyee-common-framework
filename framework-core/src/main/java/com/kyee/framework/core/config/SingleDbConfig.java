package com.kyee.framework.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 梁志艳
 * 创建时间：2015-09-08 上午11:48
 * 任务号：MOBILEDEVELOP-10307
 * 创建原因：单数据源的统一配置
 */
@Configuration
@EnableConfigurationProperties(SingleDbProperties.class)
public class SingleDbConfig {
    private static final Log logger = LogFactory.getLog(SingleDbConfig.class);
    @Autowired
    private SingleDbProperties singleDbProperties;

    /**
     * 使用DruidDataSource作为数据源，不用配置Driver
     * @return 数据源
     */
    @Bean
    public DruidDataSource dataSource(){
        logger.debug(singleDbProperties.getUrl());
        logger.debug(singleDbProperties.getUsername());
        logger.debug(singleDbProperties.getPassword());
        logger.debug(singleDbProperties.getMaxActive());
        DruidDataSource ds = new DruidDataSource();
        ds.setUsername(singleDbProperties.getUsername());
        ds.setPassword(singleDbProperties.getPassword());
        ds.setUrl(singleDbProperties.getUrl());
        if(singleDbProperties.getMaxActive()!=-1){
            ds.setMaxActive(singleDbProperties.getMaxActive());
        }
        return ds;
    }

    /**
     * 使用工厂方法创建SqlSessionFactory，注意如果直接返回SqlSessionFactoryBean会导致Spring容器初始化错误
     * @return 返回SqlSessionFactory实例
     * @throws Exception {@link SqlSessionFactoryBean#getObject()}导致的异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }
}
