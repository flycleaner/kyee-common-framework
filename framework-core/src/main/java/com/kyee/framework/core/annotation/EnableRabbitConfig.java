package com.kyee.framework.core.annotation;

import com.kyee.framework.core.web.async.RabbitMqConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 夏之阳
 * 创建时间：2015-09-08 16:44
 * 任务号：MOBILEDEVELOP-10294
 * 创建原因：自动配置RabbitMq的注解
 * 说明：把这个注解加载应用的Application类中，可自动配置rabbitmq
 * 注意：发送端只需要添加EnableRabbitConfig，接收端需要在接收处理函数上加上@RabbitListener(queues = QUEUE_NAME)
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RabbitMqConfig.class)
public @interface EnableRabbitConfig {
}
