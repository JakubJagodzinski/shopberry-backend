package com.example.shopberry.domain.orders;

import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.orders.dto.CreateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.OrderDtoMapper;
import com.example.shopberry.domain.orders.dto.OrderResponseDto;
import com.example.shopberry.domain.orderstatuses.OrderStatusRepository;
import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.paymenttypes.PaymentTypeRepository;
import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import com.example.shopberry.domain.shipmenttypes.ShipmentTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipmentTypeRepository shipmentTypeRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final OrderDtoMapper orderDtoMapper;

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private static final String SHIPMENT_TYPE_NOT_FOUND_MESSAGE = "Shipment type not found";
    private static final String PAYMENT_TYPE_NOT_FOUND_MESSAGE = "Payment type not found";

    public List<OrderResponseDto> getAllOrders() {
        return orderDtoMapper.toDtoList(orderRepository.findAll());
    }

    @Transactional
    public OrderResponseDto getOrderById(Long id) throws EntityNotFoundException {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        return orderDtoMapper.toDto(order);
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(createOrderRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        ShipmentType shipmentType = shipmentTypeRepository.findById(createOrderRequestDto.getShipmentTypeId()).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(SHIPMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        PaymentType paymentType = paymentTypeRepository.findById(createOrderRequestDto.getPaymentTypeId()).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PAYMENT_TYPE_NOT_FOUND_MESSAGE);
        }

        Order order = new Order();
        order.setOrderStatus(orderStatusRepository.getReferenceById(1L)); // Default status
        order.setCustomer(customer);
        order.setShipmentType(shipmentType);
        order.setShipmentIdentifier(createOrderRequestDto.getShipmentIdentifier());
        order.setPaymentType(paymentType);
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
