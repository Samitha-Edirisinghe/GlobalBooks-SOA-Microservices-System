# RabbitMQ setup script for GlobalBooks SOA System (PowerShell version)

$RABBITMQ_HOST = if ($env:RABBITMQ_HOST) { $env:RABBITMQ_HOST } else { "localhost" }
$RABBITMQ_PORT = if ($env:RABBITMQ_PORT) { $env:RABBITMQ_PORT } else { "5672" }
$RABBITMQ_USER = if ($env:RABBITMQ_USER) { $env:RABBITMQ_USER } else { "guest" }
$RABBITMQ_PASS = if ($env:RABBITMQ_PASS) { $env:RABBITMQ_PASS } else { "guest" }

Write-Host "Setting up RabbitMQ exchanges and queues..."

# Create direct exchange
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare exchange name=globalbooks.direct type=direct durable=true

# Create queues with DLQ configuration
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=orders.queue durable=true arguments='{"x-dead-letter-exchange":"", "x-dead-letter-routing-key":"orders.dlq", "x-message-ttl":60000}'
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=payments.queue durable=true arguments='{"x-dead-letter-exchange":"", "x-dead-letter-routing-key":"payments.dlq", "x-message-ttl":60000}'
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=shipping.queue durable=true arguments='{"x-dead-letter-exchange":"", "x-dead-letter-routing-key":"shipping.dlq", "x-message-ttl":60000}'

# Create DLQs
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=orders.dlq durable=true
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=payments.dlq durable=true
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare queue name=shipping.dlq durable=true

# Create bindings
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare binding source=globalbooks.direct destination_type=queue destination=orders.queue routing_key=order.created
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare binding source=globalbooks.direct destination_type=queue destination=payments.queue routing_key=payment.*
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS declare binding source=globalbooks.direct destination_type=queue destination=shipping.queue routing_key=shipping.*

Write-Host "RabbitMQ setup completed!"