package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.vintago.seeder.Sim100DatabaseSeeder;
import org.vfl.vintago.seeder.Sim20DatabaseSeeder;
import org.vfl.vintago.seeder.Sim20OutlierDatabaseSeeder;
import org.vfl.vintago.seeder.Sim500DatabaseSeeder;

@Service
public class SimulationTypeDispatcher {
    @Autowired
    private Sim20DatabaseSeeder sim20DatabaseSeeder;
    @Autowired
    private Sim20OutlierDatabaseSeeder sim20OutlierDatabaseSeeder;
    @Autowired
    private Sim100DatabaseSeeder sim100DatabaseSeeder;
    @Autowired
    private Sim500DatabaseSeeder sim500DatabaseSeeder;


    public void dispatch(String simulationType) {
        switch (simulationType) {
            case "sim20":
                sim20DatabaseSeeder.seed();
                break;
            case "sim20outlier":
                sim20OutlierDatabaseSeeder.seed();
                break;
            case "sim100":
                sim100DatabaseSeeder.seed();
                break;
            case "sim500":
                sim500DatabaseSeeder.seed();
                break;
        }
    }
}
