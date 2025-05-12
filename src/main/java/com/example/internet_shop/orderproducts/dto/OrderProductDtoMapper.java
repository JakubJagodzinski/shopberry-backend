package com.example.internet_shop.orderproducts.dto;

import com.example.internet_shop.orderproducts.OrderProduct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProductDtoMapper {

    public OrderProductResponseDto toDto(OrderProduct orderProduct) {
        OrderProductResponseDto orderProductResponseDto = new OrderProductResponseDto();

        orderProductResponseDto.setOrderId(orderProduct.getOrder().getOrderId());
        orderProductResponseDto.setProductId(orderProduct.getProduct().getProductId());
        orderProductResponseDto.setProductQuantity(orderProduct.getProductQuantity());
        orderProductResponseDto.setProductPrice(orderProduct.getProductPrice());
        orderProductResponseDto.setOrderProductStatusId(orderProduct.getOrderProductStatus().getOrderProductStatusId());

        return orderProductResponseDto;
    }

    public List<OrderProductResponseDto> toDtoList(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(this::toDto)
                .toList();
    }

}
