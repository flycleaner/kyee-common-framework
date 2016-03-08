package com.kyee.framework.demo.security;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * @author 程峰
 * 创建时间:15/9/7 下午3:54
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:配置数据源
 */
@Configuration
public class DBConfig {
    /**
     * 配置数据源
     * @return 数据源的bean
     */
    @Bean
    public DataSource dataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://202.120.40.105:9003/ky_common_wechat");
        dataSource.setUsername("kyee-run");
        dataSource.setPassword("kyeepass");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }
}
