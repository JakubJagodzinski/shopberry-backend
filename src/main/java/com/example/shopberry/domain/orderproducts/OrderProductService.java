package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.domain.orderproducts.dto.AddProductToOrderRequestDto;
import com.example.shopberry.domain.orderproducts.dto.OrderProductDtoMapper;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatusRepository;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.orders.OrderRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final OrderProductDtoMapper orderProductDtoMapper;

    private static final String ORDER_PRODUCT_NOT_FOUND_MESSAGE = "Order product not found";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_ALREADY_ADDED_TO_THIS_ORDER_MESSAGE = "Product already added to this order";
    private static final String PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "Product quantity must be greater than zero";
    private static final String PRODUCT_PRICE_MUST_BE_POSITIVE_MESSAGE = "Product price must be positive";
    private static final String ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE = "Order product status not found";
    private final OrderProductStatusRepository orderProductStatusRepository;

    @Transactional
    public List<OrderProductResponseDto> getOrderAllProducts(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException(ORDER_NOT_FOUND_MESSAGE);
        }

        return orderProductDtoMapper.toDtoList(orderProductRepository.findAllByOrder_OrderId(orderId));
    }

    @Transactional
    public OrderProductResponseDto getOrderProductById(Long orderId, Long productId) throws EntityNotFoundException {
        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(null);

        if (orderProduct == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_NOT_FOUND_MESSAGE);
        }

        return orderProductDtoMapper.toDto(orderProduct);
    }

    @Transactional
    public OrderProductResponseDto addProductToOrder(Long orderId, AddProductToOrderRequestDto addProductToOrderRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(addProductToOrderRequestDto.getProductId()).orElse(null);

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

        if (addProductToOrderRequestDto.getProductQuantity() <= 0) {
            throw new IllegalArgumentException(PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
        }

        orderProduct.setProductQuantity(addProductToOrderRequestDto.getProductQuantity());

        if (addProductToOrderRequestDto.getProductPrice() < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_MUST_BE_POSITIVE_MESSAGE);
        }

        orderProduct.setProductPrice(addProductToOrderRequestDto.getProductPrice());

        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(1L).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(ORDER_PRODUCT_STATUS_NOT_FOUND_MESSAGE);
        }

        orderProduct.setOrderProductStatus(orderProductStatus);

        return orderProductDtoMapper.toDto(orderProductRepository.save(orderProduct));
    }

    @Transactional
    public void removeProductFromOrder(Long orderId, Long productId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new EntityNotFoundException(ORDER_PRODUCT_NOT_FOUND_MESSAGE);
        }

        orderProductRepository.deleteById(orderProductId);
    }

}
