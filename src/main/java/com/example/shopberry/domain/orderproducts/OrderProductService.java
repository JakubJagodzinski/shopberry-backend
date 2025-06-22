package com.example.shopberry.domain.orderproducts;

import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.orderproducts.dto.OrderProductDtoMapper;
import com.example.shopberry.domain.orderproducts.dto.OrderProductResponseDto;
import com.example.shopberry.domain.orders.OrderRepository;
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

}
