CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(255),
    order_date TIMESTAMP,
    total_amount DOUBLE,
    status VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,
    isbn VARCHAR(255),
    quantity INT,
    price DOUBLE,
    FOREIGN KEY (order_id) REFERENCES orders(id)
);