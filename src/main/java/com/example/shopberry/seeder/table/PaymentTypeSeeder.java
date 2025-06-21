package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.paymenttypes.PaymentTypeRepository;
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
public class PaymentTypeSeeder implements DataSeeder {

    private final PaymentTypeRepository paymentTypeRepository;

    private static final String PAYMENT_TYPES_DATA_FILE_PATH = DATA_DIRECTORY + "/payment_types.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (paymentTypeRepository.count() == 0) {
            List<PaymentType> types = CsvUtils.loadFromCsv(
                    PAYMENT_TYPES_DATA_FILE_PATH,
                    1,
                    parts -> {
                        PaymentType paymentType = new PaymentType();

                        paymentType.setPaymentName(parts[0].trim());

                        return paymentType;
                    }
            );

            paymentTypeRepository.saveAll(types);
        }
    }

}
