package com.kyee.framework.demo.singledb;

import com.kyee.framework.core.annotation.EnableSingleDb;
import com.kyee.framework.demo.singledb.service.DemoService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author 梁志艳
 * 创建时间：2015-09-08 下午2:03
 * 任务号：MOBILEDEVELOP-10307
 * 创建原因：single db demo程序
 * 说明：使用单数据源需要添加@EnableSingleDb注解，此外还需要通过@MapperScan声明mapper的包
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSingleDb
@MapperScan("com.kyee.framework.demo.singledb.dao.mapper")
public class SingleDBDemoApplication implements CommandLineRunner{
    @Autowired
    private DemoService demoService;

    /**
     * 程序入口
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SingleDBDemoApplication.class,args);
    }

    /**
     * 测试入口
     * @param args 命令行参数
     * @throws Exception {@link CommandLineRunner#run(String...)}报的异常
     */
    @Override
    public void run(String... args) throws Exception {
        demoService.doTest();
    }
}
