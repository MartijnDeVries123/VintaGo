package org.vfl.vintago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vfl.vintago.entity.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByStatus(String status);
}