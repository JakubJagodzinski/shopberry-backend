package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatusRepository;
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
public class OrderProductStatusSeeder implements DataSeeder {

    private final OrderProductStatusRepository orderProductStatusRepository;

    private static final String ORDER_PRODUCT_STATUSES_DATA_FILE_PATH = DATA_DIRECTORY + "/order_product_statuses.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (orderProductStatusRepository.count() == 0) {
            List<OrderProductStatus> statuses = CsvUtils.loadFromCsv(
                    ORDER_PRODUCT_STATUSES_DATA_FILE_PATH,
                    1,
                    parts -> {
                        OrderProductStatus orderProductStatus = new OrderProductStatus();

                        orderProductStatus.setStatusName(parts[0].trim());

                        return orderProductStatus;
                    }
            );

            orderProductStatusRepository.saveAll(statuses);
        }
    }

}
