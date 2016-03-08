package com.kyee.framework.core.annotation;

import com.kyee.framework.core.config.DataSourceConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/11 上午11:59
 * 任务号:MOBILEDEVELOP-10321
 * 创建说明: 启动数据源配置
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DataSourceConfig.class)
public @interface EnableDataSource {
}
