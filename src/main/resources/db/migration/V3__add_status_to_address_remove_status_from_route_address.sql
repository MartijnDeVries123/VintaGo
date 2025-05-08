ALTER TABLE address ADD COLUMN status VARCHAR(50);
ALTER TABLE route_address DROP COLUMN status;