package com.globalbooks.orders.service;

import com.globalbooks.orders.messaging.OrderMessageSender;
import com.globalbooks.orders.model.Order;
import com.globalbooks.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private CatalogServiceClient catalogServiceClient;
    
    @Autowired
    private OrderMessageSender orderMessageSender;
    
    public Order createOrder(Order order) {
        // Calculate total amount
        double total = order.getItems().stream()
                .mapToDouble(item -> {
                    double price = catalogServiceClient.getBookPrice(item.getIsbn());
                    item.setPrice(price);
                    return price * item.getQuantity();
                })
                .sum();
        
        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        
        // Send message to RabbitMQ
        orderMessageSender.sendOrderCreated(savedOrder);
        
        return savedOrder;
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}