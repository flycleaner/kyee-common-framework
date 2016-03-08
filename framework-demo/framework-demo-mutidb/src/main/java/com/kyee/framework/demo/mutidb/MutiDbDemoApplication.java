package com.kyee.framework.demo.mutidb;

import com.kyee.framework.core.annotation.EnableDataSource;
import com.kyee.framework.core.annotation.KyeeApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 程峰
 *         创建时间:15/9/11 下午1:09
 *         任务号:
 *         创建说明:
 */

@KyeeApplication
@EnableDataSource
@ComponentScan("com.kyee.framework.demo.mutidb")
public class MutiDbDemoApplication{
    public static void main(String[] args) {
        SpringApplication.run(MutiDbDemoApplication.class, args);
    }
}
