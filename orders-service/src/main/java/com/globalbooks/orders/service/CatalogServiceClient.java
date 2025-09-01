package com.globalbooks.orders.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import java.net.URL;

@Component
public class CatalogServiceClient {
    
    @Value("${catalog.service.url}")
    private String catalogServiceUrl;
    
    public double getBookPrice(String isbn) {
        try {
            URL wsdlUrl = new URL(catalogServiceUrl);
            QName qname = new QName("http://catalog.globalbooks.com/", "CatalogService");
            
            Service service = Service.create(wsdlUrl, qname);
            CatalogService catalogService = service.getPort(CatalogService.class);
            
            return catalogService.getBookPrice(isbn);
        } catch (Exception e) {
            throw new RuntimeException("Failed to call CatalogService: " + e.getMessage(), e);
        }
    }
}