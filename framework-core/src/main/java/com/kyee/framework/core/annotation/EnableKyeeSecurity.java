package com.kyee.framework.core.annotation;

import com.kyee.framework.core.web.security.config.SecurityBeanConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/9 上午10:45
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:启用security注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableWebSecurity
@Import(SecurityBeanConfig.class)
public @interface EnableKyeeSecurity {
}
