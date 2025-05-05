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

CREATE TABLE addresses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    number VARCHAR(50) NOT NULL,
    zip VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,
    lat DOUBLE,
    lng DOUBLE,
    status VARCHAR(50)
);

CREATE TABLE deliverytruck (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE routes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    deliverytruck_id INT,
    FOREIGN KEY (deliverytruck_id) REFERENCES deliverytruck(id) ON DELETE SET NULL
);

CREATE TABLE route_address (
    route_id INT NOT NULL,
    address_id INT NOT NULL,
    step_order INT NOT NULL,
    PRIMARY KEY (route_id, address_id),
    FOREIGN KEY (route_id) REFERENCES routes(id) ON DELETE CASCADE,
    FOREIGN KEY (address_id) REFERENCES addresses(id) ON DELETE CASCADE
);

CREATE TABLE vrp_solver (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    configuration TEXT
    );

CREATE TABLE simulationtype (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);