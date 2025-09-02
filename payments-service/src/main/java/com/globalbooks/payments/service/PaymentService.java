package com.globalbooks.payments.service;

import com.globalbooks.payments.model.Payment;
import com.globalbooks.payments.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Transactional
    public Payment processPayment(Payment payment) {
        // Simulate payment processing
        boolean success = Math.random() > 0.2;
        
        if (success) {
            payment.setStatus("COMPLETED");
            payment.setTransactionId(UUID.randomUUID().toString());
        } else {
            payment.setStatus("FAILED");
        }
        
        return paymentRepository.save(payment);
    }
    
    public boolean isPaymentProcessed(Long orderId) {
        return paymentRepository.existsByOrderId(orderId);
    }
}