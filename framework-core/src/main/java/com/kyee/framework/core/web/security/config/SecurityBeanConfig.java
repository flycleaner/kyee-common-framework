package com.kyee.framework.core.web.security.config;

import com.kyee.framework.core.web.security.handler.KyeeAuthenticationEntryPoint;
import com.kyee.framework.core.web.security.handler.KyeeAuthenticationFailureHandler;
import com.kyee.framework.core.web.security.user.DefaultUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:23
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明: 创建security一些必要的Bean
 */
@Configuration
@ComponentScan(basePackages = {"com.kyee.framework.core.web.security","com.kyee.framework.core.data.redis"})
public class SecurityBeanConfig {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityBeanConfig.class);


    /**
     * 认证失败处理handler
     * */
    @Bean
    public AuthenticationEntryPoint kyeeAuthenticationEntryPoint(){
        return new KyeeAuthenticationEntryPoint();
    }

    /**
     * token认证失败处理handler
     * @return AuthenticationFailureHandler
     */
    @Bean
    public AuthenticationFailureHandler kyeeAuthenticationFailureHandler(){
        return new KyeeAuthenticationFailureHandler();
    }

    /**
     * UserDetailsService
     * @param authoritiesByUsernameQuery 获取用户信息查询的sql语句
     * @param usersByUsernameQuery 获取用户权限信息查询的sql语句
     * @return UserDetailsService
     */
    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService myUserDetailsService(
            @Value("${kyee.security.userDetailsService.authoritiesByUsernameQuery:}")
            String  authoritiesByUsernameQuery,
            @Value("${kyee.security.userDetailsService.usersByUsernameQuery:}")
            String usersByUsernameQuery
    ){

        DefaultUserDetailService userDetailService = new DefaultUserDetailService();
        userDetailService.setDataSource(dataSource);

        if("".equals(authoritiesByUsernameQuery) || "".equals(usersByUsernameQuery)){
            LOGGER.warn("查找用户信息和权限的sql语句没有找到,使用默认查询语句");
            LOGGER.warn("${kyee.security.userDetailsService.authoritiesByUsernameQuery}: {}",JdbcDaoImpl.DEF_AUTHORITIES_BY_USERNAME_QUERY);
            LOGGER.warn("${kyee.security.userDetailsService.usersByUsernameQuery}: {}",JdbcDaoImpl.DEF_USERS_BY_USERNAME_QUERY);
        }else {
            userDetailService.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
            userDetailService.setUsersByUsernameQuery(usersByUsernameQuery);
        }
        userDetailService.setEnableGroups(false);
        return userDetailService;
    }




}
