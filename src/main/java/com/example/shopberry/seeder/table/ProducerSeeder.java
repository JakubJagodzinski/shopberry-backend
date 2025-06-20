package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.producers.Producer;
import com.example.shopberry.domain.producers.ProducerRepository;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.seeder.DataSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProducerSeeder implements DataSeeder {

    private final ProducerRepository producerRepository;

    private static final String PRODUCERS_DATA_FILE_PATH = DATA_DIRECTORY + "/producers.csv";

    @Override
    public void seed() throws IOException {
        if (producerRepository.count() == 0) {
            List<Producer> producers = CsvUtils.loadFromCsv(
                    PRODUCERS_DATA_FILE_PATH,
                    1,
                    parts -> {
                        Producer producer = new Producer();

                        producer.setProducerName(parts[0].trim());

                        return producer;
                    }
            );

            producerRepository.saveAll(producers);
        }
    }

}
