package org.vfl.vintago.seeder;
import org.springframework.stereotype.Component;

@Component
public class Sim20DatabaseSeeder extends SimulationDatabaseSeeder {
    @Override
    public void seed() {
        super.seed();
        importFromCsv("sim20addresses.csv");
    }
}
