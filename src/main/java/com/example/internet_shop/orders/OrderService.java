package com.example.internet_shop.orders;

import com.example.internet_shop.customers.CustomerRepository;
import com.example.internet_shop.orders.dto.CreateOrderRequestDto;
import com.example.internet_shop.orders.dto.OrderResponseDto;
import com.example.internet_shop.orders.dto.OrderDtoMapper;
import com.example.internet_shop.orderstatuses.OrderStatusRepository;
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
    private final OrderStatusRepository orderStatusRepository;

    private final OrderDtoMapper orderDtoMapper;

    private final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String SHIPMENT_TYPE_NOT_FOUND_MESSAGE = "Shipment type not found";
    private final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment type not found";

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ShipmentTypeRepository shipmentTypeRepository, PaymentTypeRepository paymentTypeRepository, OrderStatusRepository orderStatusRepository, OrderDtoMapper orderDtoMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.shipmentTypeRepository = shipmentTypeRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderDtoMapper = orderDtoMapper;
    }

    public List<OrderResponseDto> getOrders() {
        return orderDtoMapper.toDtoList(orderRepository.findAll());
    }

    @Transactional
    public OrderResponseDto getOrderById(Long id) throws EntityNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        return orderDtoMapper.toDto(orderRepository.getReferenceById(id));
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) throws EntityNotFoundException {
        Order order = new Order();

        order.setOrderStatus(orderStatusRepository.getReferenceById(1L)); // Default status

        if (!customerRepository.existsById(createOrderRequestDto.getCustomerId())) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }
        order.setCustomer(customerRepository.getReferenceById(createOrderRequestDto.getCustomerId()));

        if (!shipmentTypeRepository.existsById(createOrderRequestDto.getShipmentTypeId())) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }
        order.setShipmentType(shipmentTypeRepository.getReferenceById(createOrderRequestDto.getShipmentTypeId()));

        order.setShipmentIdentifier(createOrderRequestDto.getShipmentIdentifier());

        if (!paymentTypeRepository.existsById(createOrderRequestDto.getPaymentTypeId())) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }
        order.setPaymentType(paymentTypeRepository.getReferenceById(createOrderRequestDto.getPaymentTypeId()));

        order.setIsInvoice(createOrderRequestDto.getIsInvoice());

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
