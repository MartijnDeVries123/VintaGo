package org.vfl.vintago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vfl.vintago.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}