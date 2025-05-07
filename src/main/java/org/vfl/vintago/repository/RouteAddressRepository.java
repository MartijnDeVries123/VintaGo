package org.vfl.vintago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vfl.vintago.entity.RouteAddress;
import org.vfl.vintago.entity.RouteAddressId;

public interface RouteAddressRepository extends JpaRepository<RouteAddress, RouteAddressId> {
}
