package com.example.shopberry.orderproducts;

import com.example.shopberry.orderproducts.dto.CreateOrderProductRequestDto;
import com.example.shopberry.orderproducts.dto.OrderProductDtoMapper;
import com.example.shopberry.orderproducts.dto.OrderProductResponseDto;
import com.example.shopberry.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.orderproductstatuses.OrderProductStatusRepository;
import com.example.shopberry.orders.Order;
import com.example.shopberry.orders.OrderRepository;
import com.example.shopberry.products.Product;
import com.example.shopberry.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderProductDtoMapper orderProductDtoMapper;

    private final String ORDER_PRODUCT_NOT_FOUND_MESSAGE = "Order product not found";
    private final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String PRODUCT_ALREADY_ADDED_TO_THIS_ORDER_MESSAGE = "Product already added to this order";
    private final String PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "Product quantity must be greater than zero";
    private final String PRODUCT_PRICE_MUST_BE_POSITIVE_MESSAGE = "Product price must be positive";
    private final String ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE = "Order product status not found";
    private final OrderProductStatusRepository orderProductStatusRepository;

    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository, ProductRepository productRepository, OrderProductDtoMapper orderProductDtoMapper, OrderProductStatusRepository orderProductStatusRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderProductDtoMapper = orderProductDtoMapper;
        this.orderProductStatusRepository = orderProductStatusRepository;
    }

    @Transactional
    public List<OrderProductResponseDto> getOrderProductsByOrderId(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException(ORDER_NOT_FOUND_MESSAGE);
        }

        return orderProductDtoMapper.toDtoList(orderProductRepository.findByOrder_OrderId(orderId));
    }

    @Transactional
    public OrderProductResponseDto getOrderProductById(OrderProductId orderProductId) throws EntityNotFoundException {
        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(null);

        if (orderProduct == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_NOT_FOUND_MESSAGE);
        }

        return orderProductDtoMapper.toDto(orderProduct);
    }

    @Transactional
    public OrderProductResponseDto createOrderProduct(CreateOrderProductRequestDto createOrderProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(createOrderProductRequestDto.getOrderId()).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createOrderProductRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());

        if (orderProductRepository.existsById(orderProductId)) {
            throw new IllegalArgumentException(PRODUCT_ALREADY_ADDED_TO_THIS_ORDER_MESSAGE);
        }

        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setId(orderProductId);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);

        if (createOrderProductRequestDto.getProductQuantity() <= 0) {
            throw new IllegalArgumentException(PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
        }

        orderProduct.setProductQuantity(createOrderProductRequestDto.getProductQuantity());

        if (createOrderProductRequestDto.getProductPrice() < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MUST_BE_POSITIVE_MESSAGE);
        }

        orderProduct.setProductPrice(createOrderProductRequestDto.getProductPrice());

        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(1L).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE);
        }

        orderProduct.setOrderProductStatus(orderProductStatus);

        return orderProductDtoMapper.toDto(orderProductRepository.save(orderProduct));
    }

    @Transactional
    public void deleteOrderProductById(OrderProductId orderProductId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderProductId.getOrderId())) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(orderProductId.getProductId())) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new EntityNotFoundException(ORDER_PRODUCT_NOT_FOUND_MESSAGE);
        }

        orderProductRepository.deleteById(orderProductId);
    }

}
