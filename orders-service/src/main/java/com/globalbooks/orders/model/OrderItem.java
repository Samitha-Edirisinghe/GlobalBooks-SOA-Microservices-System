package com.globalbooks.orders.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String isbn;
    
    private Integer quantity;
    
    private Double price;
    
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    // Constructors, getters, and setters
    public OrderItem() {}
    
    public OrderItem(String isbn, Integer quantity, Double price) {
        this.isbn = isbn;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}