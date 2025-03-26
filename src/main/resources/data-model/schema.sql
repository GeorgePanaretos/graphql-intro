CREATE TABLE salespeople (
    salesperson_id SERIAL PRIMARY KEY,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    email VARCHAR(128) UNIQUE,
    phone VARCHAR(32),
    address VARCHAR(256),
    city VARCHAR(64),
    state CHAR(2),
    zipcode VARCHAR(12)
);

CREATE TABLE products (
    product_id UUID PRIMARY KEY,
    name VARCHAR(128),
    size INT,
    variety VARCHAR(32),
    price NUMERIC(6, 2), -- adjusted precision
    status VARCHAR(16)
);

CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(64),
    last_name VARCHAR(64),
    email VARCHAR(128) UNIQUE,
    phone VARCHAR(32),
    address VARCHAR(256),
    city VARCHAR(64),
    state CHAR(2),
    zipcode VARCHAR(12)
);

CREATE TABLE orders (
    order_id UUID PRIMARY KEY,
    customer_id INT REFERENCES customers(customer_id) ON DELETE CASCADE,
    salesperson_id INT REFERENCES salespeople(salesperson_id) ON DELETE SET NULL
);

CREATE TABLE order_lines (
    order_line_id SERIAL PRIMARY KEY,
    order_id UUID REFERENCES orders(order_id) ON DELETE CASCADE,
    product_id UUID REFERENCES products(product_id) ON DELETE RESTRICT,
    quantity INT
);
