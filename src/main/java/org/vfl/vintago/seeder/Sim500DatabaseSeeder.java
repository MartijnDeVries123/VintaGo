package org.vfl.vintago.seeder;

import org.springframework.stereotype.Component;

@Component
public class Sim500DatabaseSeeder extends SimulationDatabaseSeeder {
    @Override
    public void seed() {
        super.seed();
        importFromCsv("sim500addresses.csv");
    }
}
