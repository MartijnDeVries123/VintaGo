package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.vintago.entity.Route;
import org.vfl.vintago.repository.AddressRepository;
import org.vfl.vintago.util.RouteDistanceCalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Service
public class RouteLoggingService {

    @Autowired
    AddressRepository addressRepository;

    public void logResults(List<Route> schedule, String simulationType, String solver, double durationMs, String planningType) {
        String runId = String.valueOf(System.currentTimeMillis());

        // Zet de logs map op
        String logsDir = "logs";
        File dir = new File(logsDir);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new RuntimeException("Failed to create logs directory");
        }

        // Bepaal bestandsnamen
        String baseFilename = String.format("%s/%s_%s_%s", logsDir, solver, simulationType, planningType);
        String summaryFilename = baseFilename + ".csv";
        String routeDetailsFilename = baseFilename + "_routes.csv";

        // Verzamel gegevens
        int totalOrders = Math.toIntExact(addressRepository.count());
        int unplannedOrders = addressRepository.findByStatus("Unfulfilled").size();
        int plannedOrders = totalOrders - unplannedOrders;

        double totalDistance = 0;

        // Schrijf de route details
        try {
            boolean writeHeader = !new File(routeDetailsFilename).exists();
            try (FileWriter routeWriter = new FileWriter(routeDetailsFilename, true)) {
                if (writeHeader) {
                    routeWriter.write("run_id,orders,distance\n");
                }
                for (Route route : schedule) {
                    long distance = RouteDistanceCalculator.calculateTotalRouteDistance(route);
                    totalDistance += distance;

                    int addressesOnRoute = route.getRouteAddresses().size();

                    routeWriter.write(String.format(Locale.US, "%s,%d,%d\n",
                            runId, addressesOnRoute, distance));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write route CSV", e);
        }

        // Schrijf de samenvatting van de run
        try {
            boolean writeHeader = !new File(summaryFilename).exists();
            try (FileWriter summaryWriter = new FileWriter(summaryFilename, true)) {
                if (writeHeader) {
                    summaryWriter.write("run_id,duration (ms),planned orders,unplanned orders,total distance\n");
                }
                summaryWriter.write(String.format(Locale.US, "%s,%.2f,%d,%d,%.2f\n",
                        runId, durationMs, plannedOrders, unplannedOrders, totalDistance));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write summary CSV", e);
        }
    }
}
