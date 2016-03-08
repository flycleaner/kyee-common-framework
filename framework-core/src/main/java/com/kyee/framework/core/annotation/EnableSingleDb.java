package com.kyee.framework.core.annotation;

import com.kyee.framework.core.config.SingleDbConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 梁志艳
 * 创建时间：2015-09-08 下午2:04
 * 任务号：MOBILEDEVELOP-10307
 * 创建原因：启用Single Db模式的开关注解
 * 说明：把这个注解加载应用的Application类中或者加在@{@link org.springframework.context.annotation.Configuration}注解的类中，就会单数据源的自动配置
 * 注意：这个注解必须配合{@link org.mybatis.spring.annotation.MapperScan}一起使用，通过MapperScan指定扫描的包
 */


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SingleDbConfig.class)
public @interface EnableSingleDb {
}
