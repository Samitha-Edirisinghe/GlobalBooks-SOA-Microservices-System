# GlobalBooks SOA Microservices System

A comprehensive Service-Oriented Architecture system for book ordering, payment processing, and shipping.

```markdown

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


## 📋 System Overview

GlobalBooks is a distributed SOA system that handles book catalog management, order processing, payment handling, and shipping through independent yet coordinated services.

### Architecture Components
- **Catalog Service**: JAX-WS SOAP Web Service for book information
- **Orders Service**: Spring Boot REST API for order management
- **Payments Service**: Payment processing with RabbitMQ integration
- **Shipping Service**: Shipping management with message queues
- **BPEL Process**: Orchestration layer coordinating services
- **RabbitMQ**: Message broker for inter-service communication

## 🚀 Quick Start

### Running with Docker Compose

1. **Clone and setup the project**:
```bash
git clone <repository-url>
cd globalbooks-soa-system
```

2. **Build all services**:

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

```

3. **Build and start all services**:
```bash
docker-compose build
docker-compose up -d
```

4. **Verify services are running**:
```bash
docker-compose ps
```

5. **Access the services**:
- Catalog Service: http://localhost:8080/catalog-service/catalog?wsdl
- Orders Service: http://localhost:8081/orders
- Payments Service: http://localhost:8082/actuator/health
- Shipping Service: http://localhost:8083/actuator/health
- RabbitMQ Management: http://localhost:15672 (guest/guest)
- BPEL Engine: http://localhost:8084/ode

### Running with Kubernetes

1. **Ensure Kubernetes cluster is running**:
```bash
minikube start  # or use your preferred Kubernetes environment
```

2. **Deploy all components**:
```bash
kubectl apply -f kubernetes/
```

3. **Check deployment status**:
```bash
kubectl get pods
kubectl get services
```

4. **Access services via port forwarding**:
```bash
kubectl port-forward service/orders-service 8081:8081
kubectl port-forward service/catalog-service 8080:8080
```

## 🏗️ Project Structure

```
globalbooks-soa-system/
├── catalog-service/                 # JAX-WS SOAP Web Service
├── orders-service/                  # Spring Boot REST API
├── payments-service/                # Payment processing
├── shipping-service/                # Shipping management
├── bpel-process/                    # BPEL orchestration
├── integration-scripts/             # Infrastructure setup
├── tests/                           # Test assets
├── docker-compose.yml               # Multi-container setup
├── kubernetes/                      # K8s deployment files
└── README.md                        # This file
```

## 🔧 Service Details

### Catalog Service
- **Port**: 8080
- **Technology**: JAX-WS, Tomcat, SOAP
- **Features**: Book catalog management, price retrieval, stock checking
- **WSDL**: http://localhost:8080/catalog-service/catalog?wsdl

### Orders Service
- **Port**: 8081
- **Technology**: Spring Boot, REST, H2 Database
- **Features**: Order creation, retrieval, RabbitMQ integration
- **API**: http://localhost:8081/orders

### Payments Service
- **Port**: 8082
- **Technology**: Spring Boot, RabbitMQ consumer
- **Features**: Payment processing, transaction management

### Shipping Service
- **Port**: 8083
- **Technology**: Spring Boot, RabbitMQ consumer
- **Features**: Shipping management, tracking number generation

### BPEL Process Engine
- **Port**: 8084
- **Technology**: Apache ODE, BPEL orchestration
- **Features**: Coordinates between catalog and orders services

## 📊 API Documentation

### REST Endpoints (Orders Service)

**Create Order**
```
POST /orders
Content-Type: application/json

{
  "customerId": "customer123",
  "items": [
    {
      "isbn": "1234567890",
      "quantity": 2
    }
  ]
}
```

**Get Order**
```
GET /orders/{id}
```

**Get All Orders**
```
GET /orders
```

### SOAP Endpoints (Catalog Service)

**Get Book Price**
```xml
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" 
               xmlns:cat="http://catalog.globalbooks.com/">
   <soap:Body>
      <cat:getBookPrice>
         <isbn>1234567890</isbn>
      </cat:getBookPrice>
   </soap:Body>
</soap:Envelope>
```

## 🧪 Testing

### Using Postman
1. Import the Postman collection from `tests/postman/GlobalBooks-REST.json`
2. Import the environment from `tests/postman/environment.json`
3. Run the collection to test all endpoints

### Using SOAP UI
1. Import the SOAP UI project from `tests/soap-ui/GlobalBooks-SOAP.xml`
2. Test the catalog service SOAP operations

### Manual Testing
```bash
# Test catalog service
curl http://localhost:8080/catalog-service/catalog?wsdl

# Test orders service health
curl http://localhost:8081/actuator/health

# Create a test order
curl -X POST http://localhost:8081/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "test123",
    "items": [
      {
        "isbn": "1234567890",
        "quantity": 1
      }
    ]
  }'
```

## 🔄 Message Flow

1. **Order Creation**: REST call to Orders Service
2. **Price Validation**: Orders Service calls Catalog Service via SOAP
3. **Order Persistence**: Order saved to database
4. **Message Publishing**: Order details published to RabbitMQ
5. **Payment Processing**: Payments Service consumes message and processes payment
6. **Shipping Preparation**: Shipping Service consumes message and prepares shipment
7. **BPEL Orchestration**: Optional BPEL process coordination


### Database Access
H2 Console is enabled for all Spring Boot services:
- Orders Service: http://localhost:8081/h2-console
- Payments Service: http://localhost:8082/h2-console  
- Shipping Service: http://localhost:8083/h2-console

JDBC URL: `jdbc:h2:mem:testdb`
Username: `sa`
Password: `password`

## 📈 Monitoring

### Service Health
- Spring Boot Actuator: http://localhost:8081/actuator/health
- Service-specific health endpoints available on ports 8081, 8082, 8083

### RabbitMQ Monitoring
- Management Console: http://localhost:15672
- Username: `guest`
- Password: `guest`

### Logs
```bash
# View logs for specific service
docker-compose logs catalog-service
docker-compose logs orders-service

# View all logs
docker-compose logs

# Kubernetes logs
kubectl logs deployment/orders-service
```

## 🚢 Deployment

### Production Considerations

1. **Database**: Replace H2 with PostgreSQL or MySQL
2. **Message Broker**: Configure RabbitMQ for high availability
3. **Security**: Enable SSL, authentication, and authorization
4. **Monitoring**: Add Prometheus and Grafana for metrics
5. **Logging**: Implement centralized logging with ELK stack
6. **CI/CD**: Set up automated deployment pipeline

### Environment Variables

Key environment variables for configuration:
- `SPRING_RABBITMQ_HOST`: RabbitMQ hostname
- `CATALOG_SERVICE_URL`: Catalog service WSDL URL  
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_PROFILES_ACTIVE`: Active Spring profiles

### Kubernetes Deployment

1. **Create namespaces**:
```bash
kubectl create namespace globalbooks
```

2. **Deploy with namespace**:
```bash
kubectl apply -f kubernetes/ -n globalbooks
```

3. **Set up ingress** for external access
4. **Configure persistent storage** for databases and RabbitMQ
5. **Implement resource limits** and autoscaling

## 🆘 Troubleshooting

### Common Issues

1. **Services not starting**: Check Docker logs for errors
2. **Connection refused**: Verify all services are running and ports are available
3. **RabbitMQ connection issues**: Check RabbitMQ is running and credentials are correct
4. **BPEL process errors**: Verify WSDL endpoints are accessible

### Debug Tips

1. **Check service health**:
```bash
curl http://localhost:8081/actuator/health
```

2. **Verify RabbitMQ queues**:
```bash
# Access RabbitMQ management console or use CLI
rabbitmqctl list_queues
```

3. **View application logs**:
```bash
docker-compose logs [service-name]
```

4. **Check database content**:
```bash
# Access H2 console through browser
```

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

For support, please open an issue in the GitHub repository or contact the development team.

---

**Note**: This is a demonstration system for educational purposes. Not all production-ready features are implemented.
```

I've integrated all the provided details while maintaining the original structure and content. The enhanced README now includes comprehensive sections for quick start, project structure, API documentation, testing, deployment, and troubleshooting, making it much more useful for developers and users of the system.
