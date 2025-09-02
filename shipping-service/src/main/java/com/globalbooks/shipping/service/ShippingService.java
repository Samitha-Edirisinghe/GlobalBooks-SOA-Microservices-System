package com.globalbooks.shipping.service;

import com.globalbooks.shipping.model.Shipment;
import com.globalbooks.shipping.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ShippingService {
    
    @Autowired
    private ShipmentRepository shipmentRepository;
    
    @Transactional
    public Shipment scheduleShipment(Long orderId) {
        // Create a new shipment
        Shipment shipment = new Shipment(orderId);
        
        // Generate tracking number
        shipment.setTrackingNumber(generateTrackingNumber());
        
        // Save to database
        return shipmentRepository.save(shipment);
    }
    
    private String generateTrackingNumber() {
        return "GB" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}