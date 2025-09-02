package com.globalbooks.payments.listener;

import com.globalbooks.payments.model.Payment;
import com.globalbooks.payments.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @RabbitListener(queues = "payments.queue")
    public void processOrder(@Payload OrderMessage order) {
        System.out.println("Received order for payment processing: " + order.getId());
        
        // Check if payment already processed (idempotency)
        if (paymentService.isPaymentProcessed(order.getId())) {
            System.out.println("Payment already processed for order: " + order.getId());
            return;
        }
        
        // Create payment with order details
        Payment payment = new Payment(order.getId(), order.getTotalAmount());
        
        // Process payment
        Payment processedPayment = paymentService.processPayment(payment);
        
        // Publish result
        if ("COMPLETED".equals(processedPayment.getStatus())) {
            rabbitTemplate.convertAndSend("globalbooks.direct", "payment.completed", order);
            System.out.println("Payment completed for order: " + order.getId());
        } else {
            rabbitTemplate.convertAndSend("globalbooks.direct", "payment.failed", order);
            System.out.println("Payment failed for order: " + order.getId());
        }
    }
}