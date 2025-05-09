package org.vfl.algorithms;

import org.springframework.stereotype.Service;
import org.vfl.vintago.entity.Address;

import java.util.List;

@Service
public interface VrpSolver {
    List<Integer> solve(List<Address> unfulfilledOrders, String days);
    boolean canCreateMultipleRoutes();

    // Todo. Dit moet verplaatst worden naar een eigen service. Een LoggingService zou bij uitstek geschikt zijn.
//    default List<Integer> findShortestRouteTimingMethod(double[][] distanceMatrix) {
//        long startTime = System.nanoTime();
//        double elapsedTimeMs = 0;
//        List<Integer> shortestRoute = findShortestRoute(distanceMatrix);
//        long endTime = System.nanoTime();
//        long elapsedTimeNs = endTime - startTime;
//
//        if (elapsedTimeNs < 1_000_000) {
//            elapsedTimeMs = 0;
//        } else {
//            elapsedTimeMs = elapsedTimeNs / 1_000_000.0;
//        }
//
//        System.out.println("\nExecution time: " + (elapsedTimeNs) + "ns = " + String.format("%.1f", elapsedTimeMs) + "ms");
//        return shortestRoute;
//    }
}
