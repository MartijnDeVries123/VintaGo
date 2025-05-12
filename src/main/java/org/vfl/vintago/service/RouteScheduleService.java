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

import java.util.List;

@Service
public class RouteScheduleService {
    @Autowired AddressRepository addressRepository;
    @Autowired RouteRepository routeRepository;
    @Autowired SolverResolver solverResolver;
    @Autowired SimulationTypeDispatcher simulationTypeDispatcher;
    @Autowired RouteLoggingService routeLoggingService;

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

        int amountDays = days.equals("day") ? 1 : 6;

        long startTime = System.nanoTime();
        List<Route> createdSchedule = vrpSolver.solve(unfulfilledOrder, amountDays);
        long endTime = System.nanoTime();

        double durationMs = (endTime - startTime) / 1_000_000.0;

        routeLoggingService.logResults(createdSchedule, simulationType, vrpSolver.getClass().getSimpleName(), durationMs, days);
        return RouteMapper.toRouteDTOList(createdSchedule);
    }

    public void initializeSimulationEnvironment(String simulationType) {
        simulationTypeDispatcher.dispatch(simulationType);
    }

    public List<Address> getUnfulfilledOrders() {
        return addressRepository.findByStatus("Unfulfilled");
    }
}
