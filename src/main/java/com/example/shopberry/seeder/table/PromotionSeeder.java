package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.promotions.Promotion;
import com.example.shopberry.domain.promotions.PromotionRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.utils.LongParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PromotionSeeder implements DataSeeder {

    private final PromotionRepository promotionRepository;

    private static final String PROMOTIONS_DATA_FILE_PATH = DATA_DIRECTORY + "/promotions.csv";

    @Override
    public void seed() throws IOException {
        if (promotionRepository.count() == 0) {
            List<Promotion> promotions = CsvUtils.loadFromCsv(
                    PROMOTIONS_DATA_FILE_PATH,
                    2,
                    parts -> {
                        Promotion promotion = new Promotion();

                        promotion.setPromotionName(parts[0].trim());
                        promotion.setDiscountPercentValue(LongParser.parse(parts[1].trim()));

                        return promotion;
                    }
            );

            promotionRepository.saveAll(promotions);
        }
    }

}
