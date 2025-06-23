package com.example.shopberry.domain.orderproductstatuses.dto;

import com.example.shopberry.domain.orderproductstatuses.OrderProductStatus;
import com.example.shopberry.domain.orderproductstatuses.dto.response.OrderProductStatusResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProductStatusDtoMapper {

    public OrderProductStatusResponseDto toDto(OrderProductStatus orderProductStatus) {
        if (orderProductStatus == null) {
            return null;
        }

        OrderProductStatusResponseDto orderProductStatusResponseDto = new OrderProductStatusResponseDto();

        orderProductStatusResponseDto.setOrderProductStatusId(orderProductStatus.getOrderProductStatusId());
        orderProductStatusResponseDto.setStatusName(orderProductStatus.getStatusName());
        orderProductStatusResponseDto.setDescription(orderProductStatus.getDescription());

        return orderProductStatusResponseDto;
    }

    public List<OrderProductStatusResponseDto> toDtoList(List<OrderProductStatus> orderProductStatusList) {
        return orderProductStatusList.stream()
                .map(this::toDto)
                .toList();
    }

}
