package com.example.shopberry.seeder;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final CauseOfReturnRepository causeOfReturnRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ShipmentTypeRepository shipmentTypeRepository;
    private final AttributeRepository attributeRepository;
    private final PromotionRepository promotionRepository;
    private final OrderProductStatusRepository orderProductStatusRepository;

    public DatabaseSeeder(CategoryRepository categoryRepository, ProducerRepository producerRepository, PaymentTypeRepository paymentTypeRepository, CauseOfReturnRepository causeOfReturnRepository, OrderStatusRepository orderStatusRepository, ShipmentTypeRepository shipmentTypeRepository, AttributeRepository attributeRepository, PromotionRepository promotionRepository, OrderProductStatusRepository orderProductStatusRepository) {
        this.categoryRepository = categoryRepository;
        this.producerRepository = producerRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.causeOfReturnRepository = causeOfReturnRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.attributeRepository = attributeRepository;
        this.promotionRepository = promotionRepository;
        this.orderProductStatusRepository = orderProductStatusRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        seedProducers();
        seedCategories();
        seedPaymentTypes();
        seedCausesOfReturn();
        seedOrderStatuses();
        seedShipmentTypes();
        seedAttributes();
        seedPromotions();
        seedOrderProductStatus();
    }

    private void seedProducers() {
        if (producerRepository.count() == 0) {
            List<Producer> producers = new ArrayList<>();

            Producer producer1 = new Producer();
            producer1.setProducerName("Producer A");

            Producer producer2 = new Producer();
            producer2.setProducerName("Producer B");

            Producer producer3 = new Producer();
            producer3.setProducerName("Producer C");

            Producer producer4 = new Producer();
            producer4.setProducerName("Producer D");

            producers.add(producer1);
            producers.add(producer2);
            producers.add(producer3);
            producers.add(producer4);

            producerRepository.saveAll(producers);
        }
    }

    private void seedCategories() {
        if (categoryRepository.count() == 0) {
            List<Category> categories = new ArrayList<>();

            Category category1 = new Category();
            category1.setCategoryName("Fashion");

            Category category2 = new Category();
            category2.setCategoryName("Food & Beverages");

            Category category3 = new Category();
            category3.setCategoryName("Mobile & Electronics");

            Category category4 = new Category();
            category4.setCategoryName("Home & Living");

            Category category5 = new Category();
            category5.setCategoryName("Health & Beauty");

            Category category6 = new Category();
            category6.setCategoryName("Sports & Outdoors");

            Category category7 = new Category();
            category7.setCategoryName("Toys & Games");

            Category category8 = new Category();
            category8.setCategoryName("Books & Stationery");

            Category category9 = new Category();
            category9.setCategoryName("Automotive & Tools");

            Category category10 = new Category();
            category10.setCategoryName("Pet Supplies");

            Category category11 = new Category();
            category11.setCategoryName("Music & Entertainment");

            Category category12 = new Category();
            category12.setCategoryName("Gifts & Crafts");

            Category category13 = new Category();
            category13.setCategoryName("Jewelry & Accessories");

            Category category14 = new Category();
            category14.setCategoryName("Baby & Kids");

            Category category15 = new Category();
            category15.setCategoryName("Gardening & Outdoor Living");

            categories.add(category1);
            categories.add(category2);
            categories.add(category3);
            categories.add(category4);
            categories.add(category5);
            categories.add(category6);
            categories.add(category7);
            categories.add(category8);
            categories.add(category9);
            categories.add(category10);
            categories.add(category11);
            categories.add(category12);
            categories.add(category13);
            categories.add(category14);
            categories.add(category15);

            categoryRepository.saveAll(categories);
        }
    }

    private void seedPaymentTypes() {
        if (paymentTypeRepository.count() == 0) {
            List<PaymentType> paymentTypes = new ArrayList<>();

            PaymentType paymentType1 = new PaymentType();
            paymentType1.setPaymentName("Credit Card");

            PaymentType paymentType2 = new PaymentType();
            paymentType2.setPaymentName("PayPal");

            PaymentType paymentType3 = new PaymentType();
            paymentType3.setPaymentName("Bank Transfer");

            PaymentType paymentType4 = new PaymentType();
            paymentType4.setPaymentName("Cash on Delivery");

            PaymentType paymentType5 = new PaymentType();
            paymentType5.setPaymentName("Cryptocurrency");

            paymentTypes.add(paymentType1);
            paymentTypes.add(paymentType2);
            paymentTypes.add(paymentType3);
            paymentTypes.add(paymentType4);
            paymentTypes.add(paymentType5);

            paymentTypeRepository.saveAll(paymentTypes);
        }
    }

    private void seedCausesOfReturn() {
        if (causeOfReturnRepository.count() == 0) {
            List<CauseOfReturn> causesOfReturn = new ArrayList<>();

            CauseOfReturn cause1 = new CauseOfReturn();
            cause1.setCause("Defective Item");

            CauseOfReturn cause2 = new CauseOfReturn();
            cause2.setCause("Wrong Item Sent");

            CauseOfReturn cause3 = new CauseOfReturn();
            cause3.setCause("Item Not as Described");

            CauseOfReturn cause4 = new CauseOfReturn();
            cause4.setCause("Late Delivery");

            CauseOfReturn cause5 = new CauseOfReturn();
            cause5.setCause("Changed Mind");

            CauseOfReturn cause6 = new CauseOfReturn();
            cause6.setCause("Other");

            causesOfReturn.add(cause1);
            causesOfReturn.add(cause2);
            causesOfReturn.add(cause3);
            causesOfReturn.add(cause4);
            causesOfReturn.add(cause5);
            causesOfReturn.add(cause6);

            causeOfReturnRepository.saveAll(causesOfReturn);
        }
    }

    private void seedOrderStatuses() {
        if (orderStatusRepository.count() == 0) {
            List<OrderStatus> orderStatuses = new ArrayList<>();

            OrderStatus status1 = new OrderStatus();
            status1.setOrderStatusName("Pending");

            OrderStatus status2 = new OrderStatus();
            status2.setOrderStatusName("Processing");

            OrderStatus status3 = new OrderStatus();
            status3.setOrderStatusName("Shipped");

            OrderStatus status4 = new OrderStatus();
            status4.setOrderStatusName("Delivered");

            OrderStatus status5 = new OrderStatus();
            status5.setOrderStatusName("Cancelled");

            OrderStatus status6 = new OrderStatus();
            status6.setOrderStatusName("Returned");

            OrderStatus status7 = new OrderStatus();
            status7.setOrderStatusName("Refunded");

            orderStatuses.add(status1);
            orderStatuses.add(status2);
            orderStatuses.add(status3);
            orderStatuses.add(status4);
            orderStatuses.add(status5);
            orderStatuses.add(status6);
            orderStatuses.add(status7);

            orderStatusRepository.saveAll(orderStatuses);
        }
    }

    private void seedShipmentTypes() {
        if (shipmentTypeRepository.count() == 0) {
            List<ShipmentType> shipmentTypes = new ArrayList<>();

            ShipmentType shipmentType1 = new ShipmentType();
            shipmentType1.setShipmentName("Standard Shipping");
            shipmentType1.setShipmentCost(5.99);

            ShipmentType shipmentType2 = new ShipmentType();
            shipmentType2.setShipmentName("Express Shipping");
            shipmentType2.setShipmentCost(15.99);

            ShipmentType shipmentType3 = new ShipmentType();
            shipmentType3.setShipmentName("Overnight Shipping");
            shipmentType3.setShipmentCost(29.99);

            shipmentTypes.add(shipmentType1);
            shipmentTypes.add(shipmentType2);
            shipmentTypes.add(shipmentType3);

            shipmentTypeRepository.saveAll(shipmentTypes);
        }
    }

    private void seedAttributes() {
        if (attributeRepository.count() == 0) {
            List<Attribute> attributes = new ArrayList<>();

            Attribute attribute1 = new Attribute();
            attribute1.setAttributeName("Color");

            Attribute attribute2 = new Attribute();
            attribute2.setAttributeName("Size");

            Attribute attribute3 = new Attribute();
            attribute3.setAttributeName("Material");

            Attribute attribute4 = new Attribute();
            attribute4.setAttributeName("Weight");

            Attribute attribute5 = new Attribute();
            attribute5.setAttributeName("Brand");

            Attribute attribute6 = new Attribute();
            attribute6.setAttributeName("Warranty");

            attributes.add(attribute1);
            attributes.add(attribute2);
            attributes.add(attribute3);
            attributes.add(attribute4);
            attributes.add(attribute5);
            attributes.add(attribute6);

            attributeRepository.saveAll(attributes);
        }
    }

    private void seedPromotions() {
        if (promotionRepository.count() == 0) {
            List<Promotion> promotions = new ArrayList<>();

            Promotion promotion1 = new Promotion();
            promotion1.setPromotionName("Black Friday Sale");
            promotion1.setDiscountPercentValue(20L);

            Promotion promotion2 = new Promotion();
            promotion2.setPromotionName("Holiday Discount");
            promotion2.setDiscountPercentValue(15L);

            Promotion promotion3 = new Promotion();
            promotion3.setPromotionName("Summer Clearance");
            promotion3.setDiscountPercentValue(25L);

            Promotion promotion4 = new Promotion();
            promotion4.setPromotionName("Back to School");
            promotion4.setDiscountPercentValue(10L);

            Promotion promotion5 = new Promotion();
            promotion5.setPromotionName("New Year Special");
            promotion5.setDiscountPercentValue(30L);

            Promotion promotion6 = new Promotion();
            promotion6.setPromotionName("Valentine's Day Offer");
            promotion6.setDiscountPercentValue(5L);

            Promotion promotion7 = new Promotion();
            promotion7.setPromotionName("Easter Sale");
            promotion7.setDiscountPercentValue(15L);

            Promotion promotion8 = new Promotion();
            promotion8.setPromotionName("Spring Festival");
            promotion8.setDiscountPercentValue(10L);

            Promotion promotion9 = new Promotion();
            promotion9.setPromotionName("Cyber Monday");
            promotion9.setDiscountPercentValue(20L);

            Promotion promotion10 = new Promotion();
            promotion10.setPromotionName("Weekend Flash Sale");
            promotion10.setDiscountPercentValue(50L);

            promotions.add(promotion1);
            promotions.add(promotion2);
            promotions.add(promotion3);
            promotions.add(promotion4);
            promotions.add(promotion5);
            promotions.add(promotion6);
            promotions.add(promotion7);
            promotions.add(promotion8);
            promotions.add(promotion9);
            promotions.add(promotion10);

            promotionRepository.saveAll(promotions);
        }
    }

    private void seedOrderProductStatus() {
        if (orderProductStatusRepository.count() == 0) {
            List<OrderProductStatus> orderProductStatuses = new ArrayList<>();

            OrderProductStatus orderProductStatus1 = new OrderProductStatus();
            orderProductStatus1.setStatusName("Returned");

            OrderProductStatus orderProductStatus2 = new OrderProductStatus();
            orderProductStatus2.setStatusName("Refunded");

            orderProductStatuses.add(orderProductStatus1);
            orderProductStatuses.add(orderProductStatus2);

            orderProductStatusRepository.saveAll(orderProductStatuses);
        }
    }

}