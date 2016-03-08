package com.kyee.framework.demo.rabbit;

import com.kyee.framework.core.annotation.EnableExceptionManagement;
import com.kyee.framework.core.annotation.EnableRabbitConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 夏之阳
 * 创建时间：2015-09-09 下午2:00
 * 任务号：MOBILEDEVELOP-10294
 * 创建原因：创建RabbitMQ测试的application
 * 说明：通过{@link EnableExceptionManagement}提供对异常统一返回功能
 *       通过{@link EnableRabbitConfig}自动配置rabbitMq服务
 */

@SpringBootApplication
@EnableExceptionManagement
@EnableRabbitConfig
public class RabbitDemoApplication {
    /**
     * 程序入口
     * @param args 命令行运行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(RabbitDemoApplication.class);
    }
}
