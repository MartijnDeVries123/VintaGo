package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.mapper.RouteMapper;
import org.vfl.vintago.repository.RouteRepository;

import java.util.List;

@Service
public class RouteScheduleService {
    @Autowired RouteRepository routeRepository;

    public List<RouteDTO> getCompleteSchedule() {
        List<Route> routes = routeRepository.findAll();
        return RouteMapper.toRouteDTOList(routes);
    }
}
