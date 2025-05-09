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
import java.util.Objects;

@Service
public class RouteScheduleService {
    @Autowired AddressRepository addressRepository;
    @Autowired RouteRepository routeRepository;
    @Autowired SolverResolver solverResolver;
    @Autowired SimulationTypeDispatcher simulationTypeDispatcher;

    public List<RouteDTO> getCompleteSchedule() {
        List<Route> routes = routeRepository.findAll();
        return RouteMapper.toRouteDTOList(routes);
    }

    public void createSchedule(String simulationType, String solver, String days) {
        this.initializeSimulationEnvironment(simulationType);

        List<Address> unfulfilledOrder = this.getUnfulfilledOrders();
        VrpSolver vrpSolver = solverResolver.getSolver(solver);

        if (Objects.equals(days, "day")) {
            // schedule = vrp solve for 1 day
        } else if (Objects.equals(days, "week")) {
            // schedule = vrp solve for 6 days
        }

        //return schedule
    }

    public void initializeSimulationEnvironment(String simulationType) {
        simulationTypeDispatcher.dispatch(simulationType);
    }

    public List<Address> getUnfulfilledOrders() {
        return addressRepository.findByStatus("Unfulfilled");
    }

}
