package com.globalbooks.orders.messaging;

public class OrderItemMessage {
    private Long id;
    private String isbn;
    private Integer quantity;
    private Double price;
    
    // Constructors
    public OrderItemMessage() {}
    
    public OrderItemMessage(Long id, String isbn, Integer quantity, Double price) {
        this.id = id;
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
    
    // Optional: toString method for debugging
    @Override
    public String toString() {
        return "OrderItemMessage{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}