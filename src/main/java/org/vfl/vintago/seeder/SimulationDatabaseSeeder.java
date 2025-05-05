package org.vfl.vintago.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vfl.vintago.repository.AddressRepository;
import org.vfl.vintago.service.CsvAddressImportService;

@Component
public class SimulationDatabaseSeeder implements DatabaseSeeder {
    @Autowired
    private  AddressRepository addressRepository;
    @Autowired
    private  CsvAddressImportService csvAddressImportService;

    public void emptyTables() {
        addressRepository.deleteAll();
        // TODO empty routes + route_address
    }

    public void seed() {
        emptyTables();
    }

    public void importFromCsv(String file) {
        csvAddressImportService.importFromCsv(file);
    }
}
