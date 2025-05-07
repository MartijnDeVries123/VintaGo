package org.vfl.vintago.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vfl.vintago.repository.AddressRepository;
import org.vfl.vintago.repository.RouteRepository;
import org.vfl.vintago.service.CsvAddressImportService;

@Component
public abstract class SimulationDatabaseSeeder implements DatabaseSeeder {
    @Autowired
    private  AddressRepository addressRepository;
    @Autowired
    private  CsvAddressImportService csvAddressImportService;
    @Autowired
    private RouteRepository routeRepository;

    public void emptyTables() {
        routeRepository.deleteAll();
        addressRepository.deleteAll();
    }

    public void importFromCsv(String file) {
        csvAddressImportService.importFromCsv(file);
    }

    public abstract void seed();
}
