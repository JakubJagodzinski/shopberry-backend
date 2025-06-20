package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import com.example.shopberry.domain.shipmenttypes.ShipmentTypeRepository;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.DoubleParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ShipmentTypeSeeder implements DataSeeder {

    private final ShipmentTypeRepository shipmentTypeRepository;

    private static final String SHIPMENT_TYPES_DATA_FILE_PATH = DATA_DIRECTORY + "/shipment_types.csv";

    @Override
    public void seed() throws IOException {
        if (shipmentTypeRepository.count() == 0) {
            List<ShipmentType> shipmentTypes = CsvUtils.loadFromCsv(
                    SHIPMENT_TYPES_DATA_FILE_PATH,
                    2,
                    parts -> {
                        ShipmentType shipmentType = new ShipmentType();

                        shipmentType.setShipmentName(parts[0].trim());
                        shipmentType.setShipmentCost(DoubleParser.parse(parts[1]));

                        return shipmentType;
                    }
            );

            shipmentTypeRepository.saveAll(shipmentTypes);
        }
    }

}
