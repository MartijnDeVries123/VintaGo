package org.vfl.vintago.seeder;

import org.springframework.stereotype.Component;

@Component
public class Sim500DatabaseSeeder extends SimulationDatabaseSeeder {
    @Override
    public void seed() {
        emptyTables();
        importFromCsv("sim500addresses.csv");
    }
}
