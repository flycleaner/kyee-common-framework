package com.kyee.framework.demo.rabbit.Receiver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 夏之阳
 * 创建时间：2015-09-07 15:26
 * 任务号：MOBILEDEVELOP-10294
 * 创建说明：接收端
 */

@Component
public class RabbitMqListener {
    static Logger logger = LogManager.getLogger(RabbitMqListener.class);

    /**
     * 接收函数
     * @param message 收到的函数
     *
     * 说明：Message类型为spring.amqp中定义的变量结构，由两部分组成（Body，MessageProperties）
     *       获取数据时调用getBody方法获得 Byte[]格式消息，反序列化获得Object
     */
    @RabbitListener(queues = "cloud")
    public void receiveMessage(Message message) {
        logger.info("Receiving Message by annotation: " + message);
    }
}
