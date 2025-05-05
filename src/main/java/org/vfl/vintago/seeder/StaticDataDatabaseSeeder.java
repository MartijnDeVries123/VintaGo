package org.vfl.vintago.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.vfl.vintago.entity.DeliveryTruck;
import org.vfl.vintago.repository.AddressRepository;
import org.vfl.vintago.repository.DeliveryTruckRepository;
import org.vfl.vintago.service.CsvAddressImportService;


@Component
public class StaticDataDatabaseSeeder implements ApplicationRunner {

    @Autowired
    private DeliveryTruckRepository deliveryTruckRepository;
    @Autowired
    private CsvAddressImportService csvAddressImportService;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (deliveryTruckRepository.count() == 0) {
            seedDeliveryTrucks();
        }
        if (addressRepository.count() == 0) {
            csvAddressImportService.importFromCsv("sim20addresses.csv");
        }
    }

    public void seedDeliveryTrucks() {
        DeliveryTruck truck1 = new DeliveryTruck();
        truck1.setName("Truck1");
        deliveryTruckRepository.save(truck1);

        DeliveryTruck truck2 = new DeliveryTruck();
        truck2.setName("Truck2");
        deliveryTruckRepository.save(truck2);
    }

}