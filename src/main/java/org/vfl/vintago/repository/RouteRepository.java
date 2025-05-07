package org.vfl.vintago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vfl.vintago.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
