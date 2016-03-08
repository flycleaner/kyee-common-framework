package com.kyee.framework.core.annotation;

import com.kyee.framework.core.config.ExceptionConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午2:22
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因：使用自动异常处理的开关
 * 说明：把这个注解加载应用的Application类中或者加在@{@link org.springframework.context.annotation.Configuration}注解的类中，就会启动异常处理特性
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ExceptionConfig.class)
public @interface EnableExceptionManagement {
}
