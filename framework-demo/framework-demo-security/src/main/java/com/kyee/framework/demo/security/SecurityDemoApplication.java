package com.kyee.framework.demo.security;

/**
 * @author 程峰
 *         创建时间:15/9/7 下午3:58
 *         任务号:
 *         创建说明:
 */

import com.kyee.framework.core.annotation.EnableCORS;
import com.kyee.framework.core.annotation.EnableKyeeSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableKyeeSecurity
@EnableCORS
@ComponentScan("com.kyee.framework.demo.security")
public class SecurityDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
