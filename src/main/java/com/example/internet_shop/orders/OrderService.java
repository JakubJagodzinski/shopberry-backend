package com.example.internet_shop.orders;

import com.example.internet_shop.customers.CustomerRepository;
import com.example.internet_shop.paymenttypes.PaymentTypeRepository;
import com.example.internet_shop.shipmenttypes.ShipmentTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipmentTypeRepository shipmentTypeRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    private final OrderDtoMapper orderDtoMapper;

    private final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String SHIPMENT_TYPE_NOT_FOUND_MESSAGE = "Shipment type not found";
    private final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment type not found";

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ShipmentTypeRepository shipmentTypeRepository, PaymentTypeRepository paymentTypeRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    public List<OrderDto> getOrders() {
        return orderDtoMapper.toDtoList(orderRepository.findAll());
    }

    @Transactional
    public OrderDto getOrderById(Long id) throws EntityNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        return orderDtoMapper.toDto(orderRepository.getReferenceById(id));
    }

    @Transactional
    public OrderDto createOrder(CreateOrderDto createOrderDto) throws EntityNotFoundException {
        Order order = new Order();

        order.setOrderId(createOrderDto.getOrderId());

        if (!customerRepository.existsById(createOrderDto.getCustomerId())) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        order.setCustomer(customerRepository.getReferenceById(createOrderDto.getCustomerId()));

        if (!shipmentTypeRepository.existsById(createOrderDto.getShipmentTypeId())) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }
        order.setShipmentType(shipmentTypeRepository.getReferenceById(createOrderDto.getShipmentTypeId()));

        order.setShipmentIdentifier(createOrderDto.getShipmentIdentifier());

        if (!paymentTypeRepository.existsById(createOrderDto.getPaymentTypeId())) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }
        order.setPaymentType(paymentTypeRepository.getReferenceById(createOrderDto.getPaymentTypeId()));

        order.setIsInvoice(createOrderDto.getIsInvoice());

        return orderDtoMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrderById(Long id) throws EntityNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        orderRepository.deleteById(id);
    }

}
