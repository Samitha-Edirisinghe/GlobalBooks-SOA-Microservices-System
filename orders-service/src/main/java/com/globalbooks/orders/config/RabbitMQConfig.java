package com.globalbooks.orders.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_NAME = "globalbooks.direct";
    public static final String ORDERS_QUEUE = "orders.queue";
    public static final String ORDERS_DLQ = "orders.dlq";
    public static final String ORDERS_ROUTING_KEY = "order.created";
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Queue ordersQueue() {
        return QueueBuilder.durable(ORDERS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", ORDERS_DLQ)
                .build();
    }
    
    @Bean
    public Queue ordersDLQ() {
        return new Queue(ORDERS_DLQ, true);
    }
    
    @Bean
    public Binding binding(Queue ordersQueue, DirectExchange exchange) {
        return BindingBuilder.bind(ordersQueue)
                .to(exchange)
                .with(ORDERS_ROUTING_KEY);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}