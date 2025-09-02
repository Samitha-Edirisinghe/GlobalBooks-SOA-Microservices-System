CREATE TABLE IF NOT EXISTS shipments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    tracking_number VARCHAR(255),
    status VARCHAR(50),
    estimated_delivery TIMESTAMP,
    shipped_date TIMESTAMP
);