# GlobalBooks SOA System

A comprehensive Service-Oriented Architecture system for book ordering, payment processing, and shipping.

## System Overview

This system consists of:
1. **Catalog Service**: SOAP-based service for book information and pricing
2. **Orders Service**: REST-based service for order management
3. **Payments Service**: Message-driven service for payment processing
4. **Shipping Service**: Message-driven service for shipment scheduling
5. **BPEL Process**: Orchestration process for order placement
6. **RabbitMQ**: Message broker for asynchronous communication

## Prerequisites

- Java 17.0.12
- Apache Maven 3.9.11
- Docker and Docker Compose
- Kubernetes (for Kubernetes deployment)
- RabbitMQ (included in Docker Compose)

## Building the System

### 1. Build all services

```bash
# Build Catalog Service
cd catalog-service
mvn clean package

# Build Orders Service
cd ../orders-service
mvn clean package

# Build Payments Service
cd ../payments-service
mvn clean package

# Build Shipping Service
cd ../shipping-service
mvn clean package

# Build BPEL Process
cd ../bpel-process
mvn clean package

# Build the Images
docker-compose build

