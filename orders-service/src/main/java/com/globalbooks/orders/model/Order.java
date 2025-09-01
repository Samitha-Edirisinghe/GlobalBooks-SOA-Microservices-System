package com.globalbooks.orders.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String customerId;
    
    private LocalDateTime orderDate;
    
    private Double totalAmount;
    
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "order")
    private List<OrderItem> items;
    
    // Constructors, getters, and setters
    public Order() {}
    
    public Order(String customerId, List<OrderItem> items) {
        this.customerId = customerId;
        this.items = items;
        for (OrderItem item : items) {
            item.setOrder(this);
        }
        this.orderDate = LocalDateTime.now();
        this.status = "CREATED";
        this.totalAmount = calculateTotalAmount();
    }
    
    private Double calculateTotalAmount() {
        return items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
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
    
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { 
        this.items = items;
        for (OrderItem item : items) {
            item.setOrder(this);
        }
        this.totalAmount = calculateTotalAmount();
    }
}