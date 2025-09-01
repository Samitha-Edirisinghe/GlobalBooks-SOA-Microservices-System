package com.globalbooks.orders.messaging;

import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.model.OrderItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.globalbooks.orders.config.RabbitMQConfig.EXCHANGE_NAME;
import static com.globalbooks.orders.config.RabbitMQConfig.ORDERS_ROUTING_KEY;

@Component
public class OrderMessageSender {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendOrderCreated(Order order) {
        OrderMessage orderMessage = convertToOrderMessage(order);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ORDERS_ROUTING_KEY, orderMessage);
    }
    
    private OrderMessage convertToOrderMessage(Order order) {
        OrderMessage message = new OrderMessage();
        message.setId(order.getId());
        message.setCustomerId(order.getCustomerId());
        message.setOrderDate(order.getOrderDate());
        message.setTotalAmount(order.getTotalAmount());
        message.setStatus(order.getStatus());
        message.setItems(order.getItems().stream()
                .map(this::convertToOrderItemMessage)
                .collect(Collectors.toList()));
        return message;
    }
    
    private OrderItemMessage convertToOrderItemMessage(OrderItem item) {
        OrderItemMessage itemMessage = new OrderItemMessage();
        itemMessage.setId(item.getId());
        itemMessage.setIsbn(item.getIsbn());
        itemMessage.setQuantity(item.getQuantity());
        itemMessage.setPrice(item.getPrice());
        return itemMessage;
    }
}