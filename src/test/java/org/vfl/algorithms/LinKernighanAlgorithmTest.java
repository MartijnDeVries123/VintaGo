package org.vfl.algorithms;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.repository.DeliveryTruckRepository;
import org.vfl.vintago.repository.RouteRepository;
import org.vfl.vintago.service.ClusterOrdersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.vfl.algorithms.VrpSolver.WINDESHEIM_DEPOT;


class LinKernighanAlgorithmTest {

    private final LinKernighanAlgorithm algorithm = new LinKernighanAlgorithm() {{
        this.deliveryTruckRepository = new DeliveryTruckRepositoryStub();
        this.clusterOrdersService = new ClusterOrdersServiceStub();
        this.routeRepository = new RouteRepositoryStub();
    }};

    @Test
    void testSolveCreatesRoutes() {
        // dummy data
        Address a1 = new Address();
        a1.setLat(52.5098);
        a1.setLng(6.0944);

        Address a2 = new Address();
        a2.setLat(52.5121);
        a2.setLng(6.0989);

        Address a3 = new Address();
        a3.setLat(52.5112);
        a3.setLng(6.0963);

        List<Address> orders = List.of(a1, a2, a3);

        List<Route> routes = algorithm.solve(orders, 1);

        //Verify output
        assertNotNull(routes);
        assertEquals(1, routes.size());
        assertEquals(3, routes.get(0).getRouteAddresses().size()); // geen depot
    }

    // Dummy repositories & services
    static class DeliveryTruckRepositoryStub implements DeliveryTruckRepository {
        @Override
        public <S extends DeliveryTruck> S save(S entity) {
            return null;
        }

        @Override
        public <S extends DeliveryTruck> List<S> saveAll(Iterable<S> entities) {
            return List.of();
        }

        @Override
        public Optional<DeliveryTruck> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override public List<DeliveryTruck> findAll() {
            DeliveryTruck truck = new DeliveryTruck("Test Truck");
            truck.setName("Test Truck");
            return List.of(truck);
        }

        @Override
        public List<DeliveryTruck> findAllById(Iterable<Long> longs) {
            return List.of();
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(DeliveryTruck entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends DeliveryTruck> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<DeliveryTruck> findByName(String name) {
            return Optional.empty();
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends DeliveryTruck> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends DeliveryTruck> List<S> saveAllAndFlush(Iterable<S> entities) {
            return List.of();
        }

        @Override
        public void deleteAllInBatch(Iterable<DeliveryTruck> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> longs) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public DeliveryTruck getOne(Long aLong) {
            return null;
        }

        @Override
        public DeliveryTruck getById(Long aLong) {
            return null;
        }

        @Override
        public DeliveryTruck getReferenceById(Long aLong) {
            return null;
        }

        @Override
        public <S extends DeliveryTruck> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends DeliveryTruck> List<S> findAll(Example<S> example) {
            return List.of();
        }

        @Override
        public <S extends DeliveryTruck> List<S> findAll(Example<S> example, Sort sort) {
            return List.of();
        }

        @Override
        public <S extends DeliveryTruck> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends DeliveryTruck> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends DeliveryTruck> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends DeliveryTruck, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public List<DeliveryTruck> findAll(Sort sort) {
            return List.of();
        }

        @Override
        public Page<DeliveryTruck> findAll(Pageable pageable) {
            return null;
        }
        // ... andere methodes kun je default leeg laten of UnsupportedOperationException gooien
    }

    static class RouteRepositoryStub implements RouteRepository {
        private final List<Route> savedRoutes = new ArrayList<>();
        @Override public <S extends Route> S save(S entity) {
            savedRoutes.add(entity);
            return entity;
        }

        @Override
        public void flush() {

        }

        @Override
        public <S extends Route> S saveAndFlush(S entity) {
            return null;
        }

        @Override
        public <S extends Route> List<S> saveAllAndFlush(Iterable<S> entities) {
            return List.of();
        }

        @Override
        public void deleteAllInBatch(Iterable<Route> entities) {

        }

        @Override
        public void deleteAllByIdInBatch(Iterable<Long> longs) {

        }

        @Override
        public void deleteAllInBatch() {

        }

        @Override
        public Route getOne(Long aLong) {
            return null;
        }

        @Override
        public Route getById(Long aLong) {
            return null;
        }

        @Override
        public Route getReferenceById(Long aLong) {
            return null;
        }

        @Override
        public <S extends Route> Optional<S> findOne(Example<S> example) {
            return Optional.empty();
        }

        @Override
        public <S extends Route> List<S> findAll(Example<S> example) {
            return List.of();
        }

        @Override
        public <S extends Route> List<S> findAll(Example<S> example, Sort sort) {
            return List.of();
        }

        @Override
        public <S extends Route> Page<S> findAll(Example<S> example, Pageable pageable) {
            return null;
        }

        @Override
        public <S extends Route> long count(Example<S> example) {
            return 0;
        }

        @Override
        public <S extends Route> boolean exists(Example<S> example) {
            return false;
        }

        @Override
        public <S extends Route, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
            return null;
        }

        @Override
        public <S extends Route> List<S> saveAll(Iterable<S> entities) {
            return List.of();
        }

        @Override
        public Optional<Route> findById(Long aLong) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(Long aLong) {
            return false;
        }

        @Override
        public List<Route> findAll() {
            return List.of();
        }

        @Override
        public List<Route> findAllById(Iterable<Long> longs) {
            return List.of();
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(Long aLong) {

        }

        @Override
        public void delete(Route entity) {

        }

        @Override
        public void deleteAllById(Iterable<? extends Long> longs) {

        }

        @Override
        public void deleteAll(Iterable<? extends Route> entities) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public List<Route> findAll(Sort sort) {
            return List.of();
        }

        @Override
        public Page<Route> findAll(Pageable pageable) {
            return null;
        }
    }

    static class ClusterOrdersServiceStub extends ClusterOrdersService {
        @Override public List<List<Address>> clusterOrders(List<Address> orders) {
            List<List<Address>> clusters = new ArrayList<>();
            clusters.add(orders);
            return clusters;
        }

        @Override public void setClusterSize(int clusterSize) {
        }
    }
}