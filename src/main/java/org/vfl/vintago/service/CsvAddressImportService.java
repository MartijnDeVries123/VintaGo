package org.vfl.vintago.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vfl.vintago.dto.CoordinateDTO;
import org.vfl.vintago.entity.Address;
import org.vfl.vintago.repository.AddressRepository;
import java.io.*;

@Service
public class CsvAddressImportService {
    @Autowired
    private AddressRepository addressRepository;

    public void importFromCsv(String file) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("addresses/" + file);
        try {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split(",");

                    Address address = new Address();
                    address.setStreet(fields[0].trim());
                    address.setNumber(fields[1].trim());
                    address.setZip(fields[2].trim());
                    address.setCity(fields[3].trim());
                    address.setLocation(new CoordinateDTO(Double.parseDouble(fields[4].trim()), Double.parseDouble(fields[5].trim())));
                    address.setStatus("Unfulfilled");

                    addressRepository.save(address);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
