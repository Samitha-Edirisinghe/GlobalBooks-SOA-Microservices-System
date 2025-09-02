# Initialize RabbitMQ queues with sample data (PowerShell version)

$RABBITMQ_HOST = if ($env:RABBITMQ_HOST) { $env:RABBITMQ_HOST } else { "localhost" }
$RABBITMQ_PORT = if ($env:RABBITMQ_PORT) { $env:RABBITMQ_PORT } else { "5672" }
$RABBITMQ_USER = if ($env:RABBITMQ_USER) { $env:RABBITMQ_USER } else { "guest" }
$RABBITMQ_PASS = if ($env:RABBITMQ_PASS) { $env:RABBITMQ_PASS } else { "guest" }

Write-Host "Initializing RabbitMQ queues..."

# Purge all queues to start fresh
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=orders.queue
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=payments.queue
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=shipping.queue
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=orders.dlq
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=payments.dlq
rabbitmqadmin -H $RABBITMQ_HOST -P $RABBITMQ_PORT -u $RABBITMQ_USER -p $RABBITMQ_PASS purge queue name=shipping.dlq

Write-Host "Queues initialized and purged!"