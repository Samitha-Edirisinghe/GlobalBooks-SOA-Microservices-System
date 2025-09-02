package com.globalbooks.payments.listener;

import java.time.LocalDateTime;
import java.util.List;

public class OrderMessage {
    private Long id;
    private String customerId;
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status;
    private List<OrderItemMessage> items;
    
    // Constructors
    public OrderMessage() {}
    
    public OrderMessage(Long id, String customerId, LocalDateTime orderDate, 
                       Double totalAmount, String status, List<OrderItemMessage> items) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.items = items;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
    
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public List<OrderItemMessage> getItems() { return items; }
    public void setItems(List<OrderItemMessage> items) { this.items = items; }
}