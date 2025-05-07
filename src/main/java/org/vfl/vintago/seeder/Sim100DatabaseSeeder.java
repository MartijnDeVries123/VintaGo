package org.vfl.vintago.seeder;

import org.springframework.stereotype.Component;

@Component
public class Sim100DatabaseSeeder extends SimulationDatabaseSeeder{
    @Override
    public void seed() {
        emptyTables();
        importFromCsv("sim100addresses.csv");
    }
}
