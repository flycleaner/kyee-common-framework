package com.kyee.framework.core.web.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author 程峰
 * 创建时间:15/9/22 下午3:25
 * 任务号: MOBILEDEVELOP-10006
 * 创建说明: 供用户继承配置http拦截规则
 */
public abstract class AbstractSecurityConfig {
    /**
     * 用户自定义配置HttpSecurity接口
     * @param http HttpSecurity
     * @throws Exception
     */
    protected abstract void configureHttpProxy(HttpSecurity http) throws Exception;
}
