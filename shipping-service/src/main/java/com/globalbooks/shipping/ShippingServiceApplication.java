package com.globalbooks.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan("com.globalbooks.shipping.model")
@EnableJpaRepositories("com.globalbooks.shipping.repository")
public class ShippingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }
}

@RestController
class HealthController {
    @GetMapping("/")
    public String health() {
        return "Service is running";
    }
    
    @GetMapping("/actuator/health")
    public String actuatorHealth() {
        return "{\"status\": \"UP\"}";
    }
}