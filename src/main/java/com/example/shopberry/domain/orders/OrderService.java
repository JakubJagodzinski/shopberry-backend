package com.example.shopberry.domain.orders;

import com.example.shopberry.auth.access.manager.OrderAccessManager;
import com.example.shopberry.common.constants.messages.*;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.orderproducts.OrderProduct;
import com.example.shopberry.domain.orderproducts.OrderProductId;
import com.example.shopberry.domain.orderproducts.OrderProductRepository;
import com.example.shopberry.domain.orderproducts.dto.request.AddProductToOrderRequestDto;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatusRepository;
import com.example.shopberry.domain.orders.dto.OrderDtoMapper;
import com.example.shopberry.domain.orders.dto.request.CreateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.request.UpdateOrderRequestDto;
import com.example.shopberry.domain.orders.dto.response.OrderResponseDto;
import com.example.shopberry.domain.orderstatuses.OrderStatus;
import com.example.shopberry.domain.orderstatuses.OrderStatusRepository;
import com.example.shopberry.domain.paymenttypes.PaymentType;
import com.example.shopberry.domain.paymenttypes.PaymentTypeRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.domain.shipmenttypes.ShipmentType;
import com.example.shopberry.domain.shipmenttypes.ShipmentTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ProductRepository productRepository;

    private final OrderDtoMapper orderDtoMapper;

    private final OrderAccessManager orderAccessManager;
    private final OrderProductRepository orderProductRepository;
    private final OrderProductStatusRepository orderProductStatusRepository;

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

        order.setOrderStatus(orderStatusRepository.getReferenceById(1L)); // pending
        order.setCustomer(customer);
        order.setShipmentType(shipmentType);
        order.setPaymentType(paymentType);

        if (createOrderRequestDto.getShipmentIdentifier() != null) {
            order.setShipmentIdentifier(createOrderRequestDto.getShipmentIdentifier().trim());
        }

        if (createOrderRequestDto.getIsInvoice() != null) {
            order.setIsInvoice(createOrderRequestDto.getIsInvoice());
        }

        Order savedOrder = orderRepository.save(order);

        List<OrderProduct> orderProductList = new ArrayList<>();

        for (AddProductToOrderRequestDto addProductToOrderRequestDto : createOrderRequestDto.getProducts()) {
            OrderProduct orderProduct = new OrderProduct();

            Product product = productRepository.findById(addProductToOrderRequestDto.getProductId()).orElse(null);

            if (product == null) {
                continue;
            }

            OrderProductId orderProductId = new OrderProductId(savedOrder.getOrderId(), product.getProductId());

            orderProduct.setId(orderProductId);
            orderProduct.setOrder(savedOrder);
            orderProduct.setProduct(product);
            orderProduct.setProductPrice(addProductToOrderRequestDto.getProductPrice());
            orderProduct.setProductQuantity(addProductToOrderRequestDto.getProductQuantity());
            orderProduct.setOrderProductStatus(orderProductStatusRepository.getReferenceById(1L)); // pending

            orderProductList.add(orderProduct);
        }

        orderProductRepository.saveAll(orderProductList);

        return orderDtoMapper.toDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrderById(Long orderId, @Valid UpdateOrderRequestDto updateOrderRequestDto) throws EntityNotFoundException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        if (updateOrderRequestDto.getOrderStatusId() != null) {
            OrderStatus orderStatus = orderStatusRepository.findById(updateOrderRequestDto.getOrderStatusId()).orElse(null);

            if (orderStatus == null) {
                throw new EntityNotFoundException(OrderStatusMessages.ORDER_STATUS_NOT_FOUND);
            }

            order.setOrderStatus(orderStatus);
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
