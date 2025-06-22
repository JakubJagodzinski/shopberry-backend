package com.example.shopberry.domain.orderproducts.dto;

import com.example.shopberry.domain.orderproducts.OrderProduct;
import com.example.shopberry.domain.orderproductstatuses.dto.OrderProductStatusDtoMapper;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderProductDtoMapper {

    private final OrderProductStatusDtoMapper orderProductStatusDtoMapper;
    private final ProductDtoMapper productDtoMapper;

    public OrderProductResponseDto toDto(OrderProduct orderProduct) {
        if (orderProduct == null) {
            return null;
        }

        OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto();

        orderProductResponseDto.setOrderId(orderProduct.getOrder().getOrderId());
        orderProductResponseDto.setProduct(productDtoMapper.toDto(orderProduct.getProduct()));
        orderProductResponseDto.setProductQuantity(orderProduct.getProductQuantity());
        orderProductResponseDto.setProductPrice(orderProduct.getProductPrice());
        orderProductResponseDto.setOrderProductStatus(orderProductStatusDtoMapper.toDto(orderProduct.getOrderProductStatus()));

        return orderProductResponseDto;
    }

    public List<OrderProductResponseDto> toDtoList(List<OrderProduct> orderProductList) {
        return orderProductList.stream()
                .map(this::toDto)
                .toList();
    }

}
