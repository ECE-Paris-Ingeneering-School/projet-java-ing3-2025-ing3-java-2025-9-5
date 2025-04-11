CREATE DATABASE ShoppingDB;
USE ShoppingDB;


-- Users: Stores information about customers (new and returning) and admins
DROP TABLE IF EXISTS `Users`;
CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    user_type ENUM('client', 'admin') NOT NULL
);

--Shopping Cart
DROP TABLE IF EXISTS `ShoppingCart`;
CREATE TABLE IF NOT EXISTS ShoppingCart (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    user_type ENUM('client', 'admin') NOT NULL
);

-- Products: Stores information about items available for purchase
DROP TABLE IF EXISTS `Products`;
CREATE TABLE IF NOT EXISTS Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    brand VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    bulk_price DECIMAL(10, 2),
    bulk_quantity INT,
    stock INT NOT NULL
);

-- Orders: Records customer orders
DROP TABLE IF EXISTS `Orders`;
CREATE TABLE IF NOT EXISTS Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- OrderDetails: Stores the products in each order
DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE IF NOT EXISTS OrderDetails (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,
    price_per_unit DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- Discounts: Defines available discounts
DROP TABLE IF EXISTS `Discounts`;
CREATE TABLE IF NOT EXISTS Discounts (
    discount_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT,
    bulk_quantity INT,
    bulk_price DECIMAL(10, 2),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- ActivityLogs: Keeps track of admin operations
DROP TABLE IF EXISTS `ActivityLogs`;
CREATE TABLE IF NOT EXISTS ActivityLogs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id INT,
    action VARCHAR(255),
    log_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES Users(user_id)
);