package com.globalbooks.shipping.listener;

import com.globalbooks.shipping.model.Shipment;
import com.globalbooks.shipping.service.ShippingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ShippingListener {
    
    @Autowired
    private ShippingService shippingService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @RabbitListener(queues = "shipping.queue")
    public void processPayment(@Payload OrderMessage order) {
        System.out.println("Received payment completed for shipping: " + order.getId());
        
        // Schedule shipment
        Shipment shipment = shippingService.scheduleShipment(order.getId());
        
        System.out.println("Shipment scheduled for order: " + order.getId() + 
                          ", tracking: " + shipment.getTrackingNumber());
        
        // Publish shipping scheduled event
        rabbitTemplate.convertAndSend("globalbooks.direct", "shipping.scheduled", shipment);
    }
}