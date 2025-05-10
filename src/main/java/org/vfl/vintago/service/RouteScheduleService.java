package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.algorithms.VrpSolver;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.mapper.RouteMapper;
import org.vfl.vintago.repository.AddressRepository;
import org.vfl.vintago.repository.RouteRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteScheduleService {
    @Autowired AddressRepository addressRepository;
    @Autowired RouteRepository routeRepository;
    @Autowired SolverResolver solverResolver;
    @Autowired SimulationTypeDispatcher simulationTypeDispatcher;
    @Autowired LoggingService loggingService;

    public List<RouteDTO> getCompleteSchedule() {
        List<Route> routes = routeRepository.findAll();
        return RouteMapper.toRouteDTOList(routes);
    }

    public RouteDTO getRouteById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Route niet gevonden met id " + id));;
        return RouteMapper.toRouteDTO(route);
    }

    public List<RouteDTO> createSchedule(String simulationType, String solver, String days) {
        this.initializeSimulationEnvironment(simulationType);

        List<Address> unfulfilledOrder = this.getUnfulfilledOrders();
        VrpSolver vrpSolver = solverResolver.getSolver(solver);

        List<Route> createdSchedule;

        int amountDays = days.equals("day") ? 1 : 6;

        long startTime = System.nanoTime();
        createdSchedule = vrpSolver.solve(unfulfilledOrder, amountDays);
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;

        logResults(simulationType, vrpSolver.getClass().getSimpleName(), durationMs, amountDays);
        return RouteMapper.toRouteDTOList(createdSchedule);
    }

    public void initializeSimulationEnvironment(String simulationType) {
        simulationTypeDispatcher.dispatch(simulationType);
    }

    public List<Address> getUnfulfilledOrders() {
        return addressRepository.findByStatus("Unfulfilled");
    }

    public List<Address> getPendingOrders() {
        return addressRepository.findByStatus("pending");
    }

    public void logResults(String simulationType, String solver, double durationMs, int days) {
        Map<String, String> context = Map.of(
                "planned_orders", String.valueOf(getPendingOrders().size()),
                "days", String.valueOf(days)
        );
        loggingService.writeLog(simulationType, solver, durationMs, context);
    }


}
