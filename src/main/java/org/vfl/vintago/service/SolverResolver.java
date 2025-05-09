package org.vfl.vintago.service;

import org.springframework.stereotype.Service;
import org.vfl.algorithms.BruteForce;
import org.vfl.algorithms.SimulatedAnnealing;
import org.vfl.algorithms.VrpSolver;

@Service
public class SolverResolver {
    public VrpSolver getSolver(String solver) {
        VrpSolver instance = switch (solver) {
            case "sa" -> new SimulatedAnnealing();
            case "bf" -> new BruteForce();
            default -> null;
        };
        return instance;
    }
}
