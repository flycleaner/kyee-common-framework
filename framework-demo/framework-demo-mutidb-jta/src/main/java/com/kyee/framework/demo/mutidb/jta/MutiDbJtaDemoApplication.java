package com.kyee.framework.demo.mutidb.jta;

import com.kyee.framework.core.annotation.KyeeApplication;
import com.kyee.framework.mutidb.jta.annotation.EnableJtaMutiDb;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 程峰
 * 创建时间:15/9/11 下午1:09
 * 任务号:MOBILEDEVELOP-10321
 * 创建说明: 多数据源分布式事务demo示例
 */

@KyeeApplication
@EnableJtaMutiDb
@ComponentScan("com.kyee.framework.demo.mutidb.jta")
public class MutiDbJtaDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MutiDbJtaDemoApplication.class, args);
    }
}
