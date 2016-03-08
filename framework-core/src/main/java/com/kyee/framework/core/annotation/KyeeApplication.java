package com.kyee.framework.core.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/11 下午4:43
 * 任务号:MOBILEDEVELOP-10321
 * 创建说明:应用使用此注解来启动应用而不应该使用 @SpringBootApplication
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        XADataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JtaAutoConfiguration.class,SecurityAutoConfiguration.class,
        FlywayAutoConfiguration.class,
        RabbitAutoConfiguration.class
})
public @interface KyeeApplication {
}
