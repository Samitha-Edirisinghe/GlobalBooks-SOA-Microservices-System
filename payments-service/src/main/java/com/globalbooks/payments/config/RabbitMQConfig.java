package com.globalbooks.payments.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    public static final String EXCHANGE_NAME = "globalbooks.direct";
    public static final String PAYMENTS_QUEUE = "payments.queue";
    public static final String PAYMENTS_DLQ = "payments.dlq";
    public static final String ORDERS_ROUTING_KEY = "order.created";
    
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Queue paymentsQueue() {
        return QueueBuilder.durable(PAYMENTS_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", PAYMENTS_DLQ)
                .build();
    }
    
    @Bean
    public Queue paymentsDLQ() {
        return new Queue(PAYMENTS_DLQ, true);
    }
    
    @Bean
    public Binding paymentBinding(Queue paymentsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(paymentsQueue)
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