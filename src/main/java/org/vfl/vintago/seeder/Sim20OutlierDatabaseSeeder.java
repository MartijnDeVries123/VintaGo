package org.vfl.vintago.seeder;
import org.springframework.stereotype.Component;

@Component
public class Sim20OutlierDatabaseSeeder extends SimulationDatabaseSeeder {
    @Override
    public void seed() {
        emptyTables();
        importFromCsv("sim20outlieraddresses.csv");
    }
}
