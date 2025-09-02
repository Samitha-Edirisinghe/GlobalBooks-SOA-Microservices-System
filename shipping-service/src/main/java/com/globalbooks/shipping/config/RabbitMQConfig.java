package com.globalbooks.shipping.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_NAME = "globalbooks.direct";
    public static final String SHIPPING_QUEUE = "shipping.queue";
    public static final String SHIPPING_DLQ = "shipping.dlq";
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Queue shippingQueue() {
        return QueueBuilder.durable(SHIPPING_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", SHIPPING_DLQ)
                .build();
    }
    
    @Bean
    public Queue shippingDLQ() {
        return new Queue(SHIPPING_DLQ, true);
    }
    
    @Bean
    public Binding shippingBinding(Queue shippingQueue, DirectExchange exchange) {
        return BindingBuilder.bind(shippingQueue)
                .to(exchange)
                .with("payment.completed");
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