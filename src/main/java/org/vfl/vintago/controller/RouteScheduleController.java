package org.vfl.vintago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.service.RouteScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/routes-schedule")
public class RouteScheduleController {
    @Autowired
    RouteScheduleService routeScheduleService;

    @GetMapping
    public ResponseEntity<List<RouteDTO>> getCompleteSchedule() {
        List<RouteDTO> schedule = routeScheduleService.getCompleteSchedule();
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/create-schedule")
    public ResponseEntity<List<RouteDTO>> createSchedule() {
        // Todo Post mapping met parameters
        List<RouteDTO> schedule = routeScheduleService.createSchedule("sim500", "ortools", "week");
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Long id) {
        RouteDTO route = routeScheduleService.getRouteById(id);
        return ResponseEntity.ok(route);
    }
}
