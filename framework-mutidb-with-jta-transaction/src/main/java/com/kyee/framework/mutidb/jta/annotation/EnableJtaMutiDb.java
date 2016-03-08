package com.kyee.framework.mutidb.jta.annotation;

import com.kyee.framework.mutidb.jta.config.DataSourceConfiguration;
import com.kyee.framework.mutidb.jta.config.TrancationConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/15 下午1:33
 * 任务号: MOBILEDEVELOP-10321
 * 创建说明: 开启多数据源分布式事务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DataSourceConfiguration.class, TrancationConfiguration.class})
public @interface EnableJtaMutiDb {
}
