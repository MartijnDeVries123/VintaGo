package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.algorithms.*;

@Service
public class SolverResolver {
    @Autowired BruteForce bruteForce;
    @Autowired OrTools orTools;
    @Autowired LinKernighanAlgorithm linKernighanAlgorithm;
    @Autowired GeneticAlgorithm geneticAlgorithm;

    public VrpSolver getSolver(String solver) {
      return  switch (solver) {
            case "bf" ->  bruteForce;
            case "ortools" -> orTools;
            case "lk" -> linKernighanAlgorithm;
            case "ga" -> geneticAlgorithm;
            default -> null;
        };

    }
}
