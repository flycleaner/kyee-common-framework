package com.kyee.framework.core.web.async;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 夏之阳
 * 创建时间：2015-09-07 14:03
 * 任务号：MOBILEDEVELOP-10294
 * 创建说明：RabbitMq服务配置
 */

@Configuration
@EnableRabbit
public class RabbitMqConfig {
    /**
     * Rabbit服务器地址
     */
    @Value("${rabbitMq.url}")
    private String url;

    /**
     * Rabbit服务器端口
     */
    @Value("${rabbitMq.port}")
    private int port;

    /**
     * Rabbit服务器注册的用户名
     */
    @Value("${rabbitMq.userName}")
    private String userName;

    /**
     * Rabbit服务器用户对应的密码
     */
    @Value("${rabbitMq.password}")
    private String password;

    /**
     * 队列名
     */
    @Value("${rabbitMq.queueName}")
    private String queueName;

    /**
     * 分发器名
     */
    @Value("${rabbitMq.exchange}")
    private String exchange;

    /**
     * 建立连接工厂
     * @return 连接工厂
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(url,port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * 声明队列 durable=true队列不会消失
     * @return 队列queue
     */
    @Bean
    public Queue queue() {
        return new Queue(queueName, true);
    }

    /**
     * 声明exchange，类型为fanout，作用是向所有绑定的队列转发消息
     * @return fanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() { return new FanoutExchange(exchange);}

    /**
     * 将消息队列与分发器进行绑定
     * @param queue 队列名
     * @param exchange 分发器名
     * @return binding
     */
    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 声明消息接收端的容器
     * @return 接收容器
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
}
