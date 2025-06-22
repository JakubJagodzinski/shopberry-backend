package com.example.shopberry.seeder;

import com.example.shopberry.seeder.table.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final AdminSeeder adminSeeder;
    private final AttributeSeeder attributeSeeder;
    private final CategorySeeder categorySeeder;
    private final OrderStatusSeeder orderStatusSeeder;
    private final OrderProductStatusSeeder orderProductStatusSeeder;
    private final PaymentTypeSeeder paymentTypeSeeder;
    private final ShipmentTypeSeeder shipmentTypeSeeder;
    private final PromotionSeeder promotionSeeder;
    private final CauseOfReturnSeeder causeOfReturnSeeder;
    private final ProducerSeeder producerSeeder;
    private final ProductSeeder productSeeder;
    private final ProductAttributeSeeder productAttributeSeeder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        adminSeeder.seed();
        attributeSeeder.seed();
        orderStatusSeeder.seed();
        orderProductStatusSeeder.seed();
        paymentTypeSeeder.seed();
        shipmentTypeSeeder.seed();
        promotionSeeder.seed();
        causeOfReturnSeeder.seed();
        producerSeeder.seed();

        categorySeeder.seed();
        productSeeder.seed();

        productAttributeSeeder.seed();
    }

}
