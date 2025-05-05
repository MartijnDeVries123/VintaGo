package org.vfl.vintago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vfl.vintago.entity.DeliveryTruck;

import java.util.Optional;

public interface DeliveryTruckRepository extends JpaRepository<DeliveryTruck, Long> {
        Optional<DeliveryTruck> findByName(String name);
}
