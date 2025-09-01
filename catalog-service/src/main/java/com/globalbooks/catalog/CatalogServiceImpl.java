package com.globalbooks.catalog;

import jakarta.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import com.globalbooks.catalog.model.Book;

@WebService(endpointInterface = "com.globalbooks.catalog.CatalogService")
public class CatalogServiceImpl implements CatalogService {
    
    private Map<String, Book> bookDatabase = new HashMap<>();
    
    public CatalogServiceImpl() {
        // Initialize with sample data
        bookDatabase.put("1234567890", new Book("1234567890", "SOA Patterns", 49.99, 10));
        bookDatabase.put("0987654321", new Book("0987654321", "Microservices Architecture", 59.99, 5));
    }

    @Override
    public Book getBookDetails(String isbn) {
        return bookDatabase.get(isbn);
    }

    @Override
    public double getBookPrice(String isbn) {
        Book book = bookDatabase.get(isbn);
        return (book != null) ? book.getPrice() : 0.0;
    }

    @Override
    public boolean checkAvailability(String isbn, int quantity) {
        Book book = bookDatabase.get(isbn);
        return (book != null) && book.getStock() >= quantity;
    }
}