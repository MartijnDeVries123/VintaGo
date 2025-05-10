package org.vfl.vintago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vfl.vintago.dto.RouteDTO;
import org.vfl.vintago.dto.ScheduleRequestDTO;
import org.vfl.vintago.service.RouteScheduleService;

import javax.print.DocFlavor;
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

    @PostMapping("/create-schedule")
    public ResponseEntity<List<RouteDTO>> createSchedule(@RequestBody ScheduleRequestDTO request) {
        List<RouteDTO> schedule = routeScheduleService.createSchedule(
                request.getSimulationType(),
                request.getSolver(),
                request.getDays()
        );
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDTO> getRouteById(@PathVariable Long id) {
        RouteDTO route = routeScheduleService.getRouteById(id);
        return ResponseEntity.ok(route);
    }
}
