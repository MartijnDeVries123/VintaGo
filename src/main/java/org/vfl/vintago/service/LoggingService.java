package org.vfl.vintago.service;

import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class LoggingService {

    private static final String OUTPUT_DIR = "logs/";

//    public <T> T logExecutionTime(String simulationType, String label, Supplier<Map<String, String>> contextSupplier, Supplier<T> supplier) {
//        long startTime = System.nanoTime();
//        T result = supplier.get();
//        long endTime = System.nanoTime();
//
//        double durationMs = (endTime - startTime) / 1_000_000.0;
//        String timestamp = Instant.now().toString();
//        Map<String, String> context = contextSupplier.get();
//
//        writeToCsv(simulationType, timestamp, label, durationMs, context);
//
//        return result;
//    }


    private void writeToCsv(String simulationType, String timestamp, String solver, double durationMs, Map<String, String> context) {
        String safeFilename = String.format("%s_%s.csv", solver, simulationType).replaceAll("[^a-zA-Z0-9_\\-\\.]", "_");
        String filepath = OUTPUT_DIR + safeFilename;
        boolean fileExists = new java.io.File(filepath).exists();

        Map<String, String> fullRow = new LinkedHashMap<>();
        fullRow.put("timestamp", timestamp);
        fullRow.put("duration_ms", String.format("%.2f", durationMs));
        fullRow.putAll(context);

        try (PrintWriter writer = new PrintWriter(new FileWriter(filepath, true))) {
            if (!fileExists) {
                writer.println(String.join(",", fullRow.keySet()));
            }
            writer.println(String.join(",", fullRow.values()));
        } catch (IOException e) {
            System.err.println("Fout bij schrijven naar CSV: " + e.getMessage());
        }
    }

    public void writeLog(String simulationType, String solver, double durationMs, Map<String, String> context) {
        String timestamp = Instant.now().toString();
        writeToCsv(simulationType, timestamp, solver,durationMs, context);
    }
}