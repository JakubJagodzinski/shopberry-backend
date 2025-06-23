package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.orderstatuses.OrderStatus;
import com.example.shopberry.domain.orderstatuses.OrderStatusRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderStatusSeeder implements DataSeeder {

    private final OrderStatusRepository orderStatusRepository;

    private static final String ORDER_STATUSES_DATA_FILE_PATH = DATA_DIRECTORY + "/order_statuses.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (orderStatusRepository.count() == 0) {
            List<OrderStatus> statuses = CsvUtils.loadFromCsv(
                    ORDER_STATUSES_DATA_FILE_PATH,
                    2,
                    parts -> {
                        OrderStatus status = new OrderStatus();

                        status.setOrderStatusName(parts[0].trim());
                        status.setDescription(parts[1].trim());

                        return status;
                    }
            );

            orderStatusRepository.saveAll(statuses);
        }
    }

}
