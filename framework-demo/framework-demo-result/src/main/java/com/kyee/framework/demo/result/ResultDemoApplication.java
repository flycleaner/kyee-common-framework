package com.kyee.framework.demo.result;

import com.kyee.framework.core.annotation.EnableExceptionManagement;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午2:00
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因：创建实例Application类的写法
 * 说明：通过{@link EnableExceptionManagement}提供对异常统一返回功能
 */
@SpringBootApplication
@EnableExceptionManagement
public class ResultDemoApplication {
    /**
     * 程序入口
     * @param args 命令行运行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ResultDemoApplication.class);
    }
}
