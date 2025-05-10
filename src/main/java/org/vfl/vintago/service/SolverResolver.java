package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.algorithms.BruteForce;
import org.vfl.algorithms.OrTools;
import org.vfl.algorithms.VrpSolver;

@Service
public class SolverResolver {
    @Autowired BruteForce bruteForce;
    @Autowired OrTools orTools;

    public VrpSolver getSolver(String solver) {
      return  switch (solver) {
            case "bf" ->  bruteForce;
            case "ortools" -> orTools;
            default -> null;
        };

    }
}
