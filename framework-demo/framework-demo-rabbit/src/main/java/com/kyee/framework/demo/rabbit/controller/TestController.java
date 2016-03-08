package com.kyee.framework.demo.rabbit.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 夏之阳
 * 创建时间：2015-09-08 13:36
 * 任务号：MOBILEDEVELOP-10294
 * 创建说明：消息队列测试接口
 */

@RestController
public class TestController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test() {
        rabbitTemplate.convertAndSend("exchange", "", "Hello World");
    }
}
