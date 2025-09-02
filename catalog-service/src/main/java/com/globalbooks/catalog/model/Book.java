package com.globalbooks.catalog.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = {"isbn", "title", "price", "stock"})
public class Book {
    private String isbn;
    private String title;
    private double price;
    private int stock;
    
    public Book() {}
    
    public Book(String isbn, String title, double price, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and setters
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}