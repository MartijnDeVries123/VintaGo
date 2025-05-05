SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS artikelen;
DROP TABLE IF EXISTS klanten;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS artikel_order;
DROP TABLE IF EXISTS bezorgwagens;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS leveringen;
DROP TABLE IF EXISTS levering_service;
DROP TABLE IF EXISTS retours;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    lat DOUBLE,
    lng DOUBLE,
    status VARCHAR(50)
);

CREATE TABLE delivery_truck (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE route (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    delivery_truck_id INT,
    FOREIGN KEY (delivery_truck_id) REFERENCES delivery_truck(id) ON DELETE SET NULL
);

CREATE TABLE route_address (
    route_id INT NOT NULL,
    address_id INT NOT NULL,
    step_order INT NOT NULL,
    PRIMARY KEY (route_id, address_id),
    FOREIGN KEY (route_id) REFERENCES route(id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

