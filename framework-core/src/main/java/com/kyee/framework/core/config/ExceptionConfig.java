package com.kyee.framework.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午2:21
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因：载入框架中的Component
 */
@Configuration
@ComponentScan("com.kyee.framework.core.exception")
public class ExceptionConfig {
}
