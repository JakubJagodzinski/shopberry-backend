package com.example.shopberry.seeder;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import com.example.shopberry.domain.causesofreturn.CauseOfReturnRepository;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatusRepository;
import com.example.shopberry.domain.orderstatuses.OrderStatus;
import com.example.shopberry.domain.orderstatuses.OrderStatusRepository;
import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.paymenttypes.PaymentTypeRepository;
import com.example.shopberry.domain.producers.Producer;
import com.example.shopberry.domain.producers.ProducerRepository;
import com.example.shopberry.domain.promotions.Promotion;
import com.example.shopberry.domain.promotions.PromotionRepository;
import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import com.example.shopberry.domain.shipmenttypes.ShipmentTypeRepository;
import com.example.shopberry.user.Role;
import com.example.shopberry.user.User;
import com.example.shopberry.user.UserRepository;
import com.example.shopberry.utils.DoubleParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final ShipmentTypeRepository shipmentTypeRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final CauseOfReturnRepository causeOfReturnRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderProductStatusRepository orderProductStatusRepository;
    private final UserRepository userRepository;
    private final AttributeRepository attributeRepository;
    private final ProducerRepository producerRepository;

    private final PasswordEncoder passwordEncoder;

    private static final String DATA_DIRECTORY = "data";

    private static final String SHIPMENT_TYPES_DATA_FILE_PATH = DATA_DIRECTORY + "/shipment_types.csv";
    private static final String PAYMENT_TYPES_DATA_FILE_PATH = DATA_DIRECTORY + "/payment_types.csv";
    private static final String CAUSES_OF_RETURN_DATA_FILE_PATH = DATA_DIRECTORY + "/causes_of_return.csv";
    private static final String ORDER_STATUSES_DATA_FILE_PATH = DATA_DIRECTORY + "/order_statuses.csv";
    private static final String ORDER_PRODUCT_STATUSES_DATA_FILE_PATH = DATA_DIRECTORY + "/order_product_statuses.csv";
    private static final String ATTRIBUTES_DATA_FILE_PATH = DATA_DIRECTORY + "/attributes.csv";
    private static final String PRODUCERS_DATA_FILE_PATH = DATA_DIRECTORY + "/producers.csv";
    private static final String PROMOTIONS_DATA_FILE_PATH = DATA_DIRECTORY + "/promotions.csv";
    private final PromotionRepository promotionRepository;

    @Override
    public void run(ApplicationArguments args) throws IOException {
        seedAdminAccount();
        loadShipmentTypesFromCsv();
        loadPaymentTypesFromCsv();
        loadCausesOfReturnFromCsv();
        loadOrderStatusesFromCsv();
        loadOrderProductStatusesFromCsv();
        loadAttributesFromCsv();
        loadProducersFromCsv();
        loadPromotionsFromCsv();
    }

    private void seedAdminAccount() {
        final String adminEmail = "admin@shopberry.com";

        if (!userRepository.existsByEmail(adminEmail)) {
            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setFirstName("Admin");
            admin.setLastName("Shopberry");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);
        }
    }

    private void loadShipmentTypesFromCsv() throws IOException {
        loadFromCsv(
                SHIPMENT_TYPES_DATA_FILE_PATH,
                shipmentTypeRepository.count(),
                parts -> {
                    ShipmentType shipmentType = new ShipmentType();

                    shipmentType.setShipmentName(parts[0].trim());
                    shipmentType.setShipmentCost(DoubleParser.parse(parts[1]));

                    return shipmentType;
                },
                shipmentTypeRepository::saveAll,
                2
        );
    }

    private void loadPaymentTypesFromCsv() throws IOException {
        loadFromCsv(
                PAYMENT_TYPES_DATA_FILE_PATH,
                paymentTypeRepository.count(),
                parts -> {
                    PaymentType paymentType = new PaymentType();

                    paymentType.setPaymentName(parts[0].trim());

                    return paymentType;
                },
                paymentTypeRepository::saveAll,
                1
        );
    }

    private void loadCausesOfReturnFromCsv() throws IOException {
        loadFromCsv(
                CAUSES_OF_RETURN_DATA_FILE_PATH,
                causeOfReturnRepository.count(),
                parts -> {
                    CauseOfReturn causeOfReturn = new CauseOfReturn();

                    causeOfReturn.setCause(parts[0].trim());

                    return causeOfReturn;
                },
                causeOfReturnRepository::saveAll,
                1
        );
    }

    private void loadOrderStatusesFromCsv() throws IOException {
        loadFromCsv(
                ORDER_STATUSES_DATA_FILE_PATH,
                orderStatusRepository.count(),
                parts -> {
                    OrderStatus orderStatus = new OrderStatus();

                    orderStatus.setOrderStatusName(parts[0].trim());

                    return orderStatus;
                },
                orderStatusRepository::saveAll,
                1
        );
    }

    private void loadOrderProductStatusesFromCsv() throws IOException {
        loadFromCsv(
                ORDER_PRODUCT_STATUSES_DATA_FILE_PATH,
                orderProductStatusRepository.count(),
                parts -> {
                    OrderProductStatus orderProductStatus = new OrderProductStatus();

                    orderProductStatus.setStatusName(parts[0].trim());

                    return orderProductStatus;
                },
                orderProductStatusRepository::saveAll,
                1
        );
    }

    private void loadAttributesFromCsv() throws IOException {
        loadFromCsv(
                ATTRIBUTES_DATA_FILE_PATH,
                attributeRepository.count(),
                parts -> {
                    Attribute attribute = new Attribute();

                    attribute.setAttributeName(parts[0].trim());

                    return attribute;
                },
                attributeRepository::saveAll,
                1
        );
    }

    private void loadProducersFromCsv() throws IOException {
        loadFromCsv(
                PRODUCERS_DATA_FILE_PATH,
                producerRepository.count(),
                parts -> {
                    Producer producer = new Producer();

                    producer.setProducerName(parts[0].trim());

                    return producer;
                },
                producerRepository::saveAll,
                1
        );
    }

    private void loadPromotionsFromCsv() throws IOException {
        loadFromCsv(
                PROMOTIONS_DATA_FILE_PATH,
                promotionRepository.count(),
                parts -> {
                    Promotion promotion = new Promotion();

                    promotion.setPromotionName(parts[0].trim());
                    promotion.setDiscountPercentValue(Long.parseLong(parts[1].trim()));

                    return promotion;
                },
                promotionRepository::saveAll,
                2
        );
    }

    private <T> void loadFromCsv(String filePath, long existingCount, Function<String[], T> mapper, Consumer<List<T>> saver, int expectedColumns) throws IOException {
        if (existingCount == 0) {
            List<T> result = new ArrayList<>();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);

            if (inputStream == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }

            try (Reader reader = new InputStreamReader(inputStream)) {
                CSVFormat format = CSVFormat.Builder.create()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setIgnoreHeaderCase(true)
                        .setTrim(true)
                        .build();

                try (CSVParser csvParser = new CSVParser(reader, format)) {
                    for (CSVRecord csvRecord : csvParser) {
                        String[] parts = new String[expectedColumns];
                        for (int i = 0; i < expectedColumns; i++) {
                            parts[i] = csvRecord.get(i);
                        }
                        result.add(mapper.apply(parts));
                    }
                }
            }

            saver.accept(result);
        }
    }

}
