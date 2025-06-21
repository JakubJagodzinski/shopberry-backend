package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.OrderProductStatusMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.orderproducts.dto.AddProductToOrderRequestDto;
import com.example.shopberry.domain.orderproducts.dto.OrderProductDtoMapper;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orderproductstatuses.OrderProductStatusRepository;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.orders.OrderRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityExistsException;
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
    private final OrderProductStatusRepository orderProductStatusRepository;

    private final OrderProductDtoMapper orderProductDtoMapper;

    @Transactional
    public List<OrderProductResponseDto> getOrderAllProducts(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new IllegalArgumentException(OrderMessages.ORDER_NOT_FOUND);
        }

        return orderProductDtoMapper.toDtoList(orderProductRepository.findAllByOrder_OrderId(orderId));
    }

    @Transactional
    public OrderProductResponseDto getOrderProductById(Long orderId, Long productId) throws EntityNotFoundException {
        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        OrderProduct orderProduct = orderProductRepository.findById(orderProductId).orElse(null);

        if (orderProduct == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER);
        }

        return orderProductDtoMapper.toDto(orderProduct);
    }

    @Transactional
    public OrderProductResponseDto addProductToOrder(Long orderId, AddProductToOrderRequestDto addProductToOrderRequestDto) throws EntityNotFoundException, EntityExistsException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        Product product = productRepository.findById(addProductToOrderRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());

        if (orderProductRepository.existsById(orderProductId)) {
            throw new EntityExistsException(ProductMessages.PRODUCT_ALREADY_ADDED_TO_THIS_ORDER);
        }

        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setId(orderProductId);
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setProductQuantity(addProductToOrderRequestDto.getProductQuantity());
        orderProduct.setProductPrice(addProductToOrderRequestDto.getProductPrice());

        OrderProductStatus orderProductStatus = orderProductStatusRepository.findById(1L).orElse(null);

        if (orderProductStatus == null) {
            throw new EntityNotFoundException(OrderProductStatusMessages.ORDER_PRODUCT_STATUS_NOT_FOUND);
        }

        orderProduct.setOrderProductStatus(orderProductStatus);

        return orderProductDtoMapper.toDto(orderProductRepository.save(orderProduct));
    }

    @Transactional
    public void removeProductFromOrder(Long orderId, Long productId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        OrderProductId orderProductId = new OrderProductId(orderId, productId);

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER);
        }

        orderProductRepository.deleteById(orderProductId);
    }

}
