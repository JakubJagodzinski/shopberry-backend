package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.seeder.DataSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AttributeSeeder implements DataSeeder {

    private final AttributeRepository attributeRepository;

    private static final String ATTRIBUTES_DATA_FILE_PATH = DATA_DIRECTORY + "/attributes.csv";

    @Override
    public void seed() throws IOException {
        if (attributeRepository.count() == 0) {
            List<Attribute> attributes = CsvUtils.loadFromCsv(
                    ATTRIBUTES_DATA_FILE_PATH,
                    1,
                    parts -> {
                        Attribute attribute = new Attribute();

                        attribute.setAttributeName(parts[0].trim());

                        return attribute;
                    }
            );

            attributeRepository.saveAll(attributes);
        }
    }

}
