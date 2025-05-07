package org.vfl.vintago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vfl.vintago.dto.SimulationTypeDTO;
import org.vfl.vintago.service.SimulationTypeDispatcher;

@RestController
@RequestMapping("/api/simulationtypes")
public class SimulationController {

    @Autowired
    SimulationTypeDispatcher dispatcher;

    @PostMapping
    public ResponseEntity<Void> initSimulation(@RequestBody SimulationTypeDTO request) {
        dispatcher.dispatch(request.getType());
        return ResponseEntity.ok().build();
    }
}