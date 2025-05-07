ALTER TABLE route_address
DROP FOREIGN KEY route_address_ibfk_1,
DROP FOREIGN KEY route_address_ibfk_2;

ALTER TABLE route_address
ADD CONSTRAINT route_address_ibfk_1
FOREIGN KEY (route_id) REFERENCES route(id) ON DELETE RESTRICT,
ADD CONSTRAINT route_address_ibfk_2
FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE RESTRICT;