package com.example.shopberry.domain.orders;

import com.example.shopberry.auth.access.manager.OrderAccessManager;
import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.PaymentTypeMessages;
import com.example.shopberry.common.constants.messages.ShipmentTypeMessages;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShipmentTypeRepository shipmentTypeRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final OrderDtoMapper orderDtoMapper;

    private final OrderAccessManager orderAccessManager;

    public List<OrderResponseDto> getAllOrders() {
        return orderDtoMapper.toDtoList(orderRepository.findAll());
    }

    @Transactional
    public OrderResponseDto getOrderById(Long orderId) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        orderAccessManager.checkCanReadOrder(order);

        return orderDtoMapper.toDto(order);
    }

    @Transactional
    public List<OrderResponseDto> getCustomerAllOrders(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        orderAccessManager.checkCanReadCustomerAllOrders(customer);

        return orderDtoMapper.toDtoList(orderRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto createOrderRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(createOrderRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        orderAccessManager.checkCanCreateOrder(customer);

        ShipmentType shipmentType = shipmentTypeRepository.findById(createOrderRequestDto.getShipmentTypeId()).orElse(null);

        if (shipmentType == null) {
            throw new EntityNotFoundException(ShipmentTypeMessages.SHIPMENT_TYPE_NOT_FOUND);
        }

        PaymentType paymentType = paymentTypeRepository.findById(createOrderRequestDto.getPaymentTypeId()).orElse(null);

        if (paymentType == null) {
            throw new EntityNotFoundException(PaymentTypeMessages.PAYMENT_TYPE_NOT_FOUND);
        }

        Order order = new Order();

        order.setOrderStatus(orderStatusRepository.getReferenceById(1L)); // Default status
        order.setCustomer(customer);
        order.setShipmentType(shipmentType);
        order.setPaymentType(paymentType);

        if (createOrderRequestDto.getShipmentIdentifier() != null) {
            order.setShipmentIdentifier(createOrderRequestDto.getShipmentIdentifier().trim());
        }

        if (createOrderRequestDto.getIsPaymentRecorded() != null) {
            order.setIsPaymentRecorded(createOrderRequestDto.getIsPaymentRecorded());
        }

        if (createOrderRequestDto.getIsInvoice() != null) {
            order.setIsInvoice(createOrderRequestDto.getIsInvoice());
        }

        return orderDtoMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    public void deleteOrderById(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        orderRepository.deleteById(orderId);
    }

}
