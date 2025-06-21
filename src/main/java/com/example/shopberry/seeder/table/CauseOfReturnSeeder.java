package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import com.example.shopberry.domain.causesofreturn.CauseOfReturnRepository;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.seeder.DataSeeder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CauseOfReturnSeeder implements DataSeeder {

    private final CauseOfReturnRepository causeOfReturnRepository;

    private static final String CAUSES_OF_RETURN_DATA_FILE_PATH = DATA_DIRECTORY + "/causes_of_return.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (causeOfReturnRepository.count() == 0) {
            List<CauseOfReturn> causes = CsvUtils.loadFromCsv(
                    CAUSES_OF_RETURN_DATA_FILE_PATH,
                    1,
                    parts -> {
                        CauseOfReturn cause = new CauseOfReturn();

                        cause.setCause(parts[0].trim());

                        return cause;
                    }
            );

            causeOfReturnRepository.saveAll(causes);
        }
    }

}
