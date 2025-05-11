package org.vfl.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.entity.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.springframework.stereotype.Component;
import org.vfl.vintago.repository.DeliveryTruckRepository;

import java.util.*;

@Component
public class GeneticAlgorithm extends VrpSolver {
    @Autowired DeliveryTruckRepository deliveryTruckRepository;

    private static final int GENERATIONS = 500;
    private static final double MUTATION_RATE = 0.05;
    private static final double CROSSOVER_RATE = 0.8;
    private static final Random random = new Random();
    private static final int VEHICLE_COUNT = 2;
    private long[][] allAddressDistanceMatrix;
    Map<Address, Integer> allAddressIndexMap;

    public List<Route> solve(List<Address> unfulfilledOrders, int days) {
        int numberOfAddresses = unfulfilledOrders.size();
        int populationSize = Math.max(numberOfAddresses, Math.min(numberOfAddresses * 5, 1000));

        List<Address> all = new ArrayList<>();
        all.add(WINDESHEIM_DEPOT);
        all.addAll(unfulfilledOrders);
        allAddressDistanceMatrix = getDistanceMatrix(all);

        List<List<Integer>> population = initializePopulation(numberOfAddresses, populationSize, days);

        List<Integer> bestChromosome = null;
        double bestDistance = Double.MAX_VALUE;

        for (int generation = 0; generation < GENERATIONS; generation++) {
            population = evolvePopulation(population, days);

            for (List<Integer> chromosome : population) {
                double distance = calculateTotalDistance(chromosome);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestChromosome = chromosome;
                }
            }
        }
        return decodeChromosome(bestChromosome, unfulfilledOrders);
    }

    private List<List<Integer>> initializePopulation(int numberOfAddresses, int populationSize, int days) {
        List<List<Integer>> population = new ArrayList<>();
        List<Integer> base = new ArrayList<>();

        for (int i = 0; i < numberOfAddresses; i++) {
            base.add(i);
        }

        for (int i = 0; i < populationSize; i++) {
            List<Integer> shuffled = new ArrayList<>(base);
            Collections.shuffle(shuffled, random);

            List<Integer> chromosome = insertSeparators(shuffled, days);
            population.add(chromosome);
        }

        return population;
    }

    private List<Integer> insertSeparators(List<Integer> genes, int days) {
        List<Integer> chromosome = new ArrayList<>(genes);

        for (int d = 0; d < days * (VEHICLE_COUNT - 1); d++) {
            int insertIndex = random.nextInt(chromosome.size() + 1);
            chromosome.add(insertIndex, -1); // VEHICLE separator
        }

        // Insert day separators (-2)
        for (int d = 1; d < days; d++) {
            int insertIndex = random.nextInt(chromosome.size() + 1);
            chromosome.add(insertIndex, -2); // DAY separator
        }

        return chromosome;
    }

    private List<List<Integer>> evolvePopulation(List<List<Integer>> population, int days) {
        List<List<Integer>> newPopulation = new ArrayList<>();

        while (newPopulation.size() < population.size()) {
            List<Integer> parent1 = selectParent(population);
            List<Integer> parent2 = selectParent(population);

            List<Integer> child;
            if (random.nextDouble() < CROSSOVER_RATE) {
                child = crossover(parent1, parent2, days);
            } else {
                child = new ArrayList<>(parent1);
            }

            if (random.nextDouble() < MUTATION_RATE) {
                mutate(child);
            }

            newPopulation.add(child);
        }

        return newPopulation;
    }

    private List<Integer> selectParent(List<List<Integer>> population) {
        double[] fitnesses = population.stream()
                .mapToDouble(chromosome -> 1.0 / (calculateTotalDistance(chromosome) + 1e-6))
                .toArray();

        double total = Arrays.stream(fitnesses).sum();
        double rand = random.nextDouble() * total;

        double cumulative = 0;
        for (int i = 0; i < fitnesses.length; i++) {
            cumulative += fitnesses[i];
            if (cumulative >= rand) return population.get(i);
        }

        return population.get(population.size() - 1);
    }

    private List<Integer> crossover(List<Integer> p1, List<Integer> p2, int days) {
        List<Integer> genes1 = p1.stream().filter(x -> x >= 0).toList();
        List<Integer> genes2 = p2.stream().filter(x -> x >= 0).toList();

        int size = genes1.size();
        int start = random.nextInt(size);
        int end = start + random.nextInt(size - start);

        Set<Integer> middle = new HashSet<>(genes1.subList(start, end));
        List<Integer> childGenes = new ArrayList<>(Collections.nCopies(size, -1));

        for (int i = start; i < end; i++) {
            childGenes.set(i, genes1.get(i));
        }

        int index = 0;
        for (int gene : genes2) {
            if (!middle.contains(gene)) {
                while (childGenes.get(index) != -1) index++;
                childGenes.set(index, gene);
            }
        }

        return insertSeparators(childGenes, days);
    }

    private void mutate(List<Integer> chromosome) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < chromosome.size(); i++) {
            if (chromosome.get(i) >= 0) indices.add(i);
        }

        if (indices.size() < 2) return;
        int i = indices.get(random.nextInt(indices.size()));
        int j = indices.get(random.nextInt(indices.size()));
        Collections.swap(chromosome, i, j);
    }

    private double calculateTotalDistance(List<Integer> chromosome) {
        double distance = 0;
        List<Integer> buffer = new ArrayList<>();

        for (Integer gene : chromosome) {
            if (gene >= 0) {
                buffer.add(gene);
            } else {
                distance += routeDistance(buffer);
                buffer.clear();
            }
        }

        distance += routeDistance(buffer);
        return distance;
    }

    private double routeDistance(List<Integer> route) {
        if (route.isEmpty()) return 0;

        double dist = 0;
        dist += allAddressDistanceMatrix[0][route.get(0)]; // Start at depot
        for (int i = 0; i < route.size() - 1; i++) {
            dist += allAddressDistanceMatrix[route.get(i)][route.get(i + 1)];
        }
        dist += allAddressDistanceMatrix[0][route.get(0)]; // end at depot
        return dist;
    }

    private List<Route> decodeChromosome(List<Integer> chromosome, List<Address> addresses) {
        List<Route> routes = new ArrayList<>();
        List<Address> buffer = new ArrayList<>();

        List<DeliveryTruck> trucks = deliveryTruckRepository.findAll();
        int vehicleIndex = 0;
        LocalDate date = LocalDate.now();

        for (Integer gene : chromosome) {
            if (gene > 0) {
                buffer.add(addresses.get(gene - 1));
            } else {
                if (!buffer.isEmpty()) {
                    Route route = saveRoute(date, trucks.get(vehicleIndex), new ArrayList<>(buffer));
                    routes.add(route);
                    buffer.clear();
                }

                if (gene == -1) {
                    vehicleIndex = (vehicleIndex + 1) % VEHICLE_COUNT;
                } else if (gene == -2) {
                    date = date.plusDays(1L);
                    vehicleIndex = 0;
                }
            }
        }

        if (!buffer.isEmpty()) {
            Route route = saveRoute(date, trucks.get(vehicleIndex), new ArrayList<>(buffer));
            routes.add(route);
        }

        return routes;
    }
}