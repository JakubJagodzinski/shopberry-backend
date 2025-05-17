package com.example.shopberry.productreturns.dto;

import com.example.shopberry.productreturns.ProductReturn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductReturnDtoMapper {

    public ProductReturnResponseDto toDto(ProductReturn productReturn) {
        ProductReturnResponseDto dto = new ProductReturnResponseDto();

        dto.setProductReturnId(productReturn.getProductReturnId());
        dto.setOrderId(productReturn.getOrder().getOrderId());
        dto.setProductId(productReturn.getProduct().getProductId());
        dto.setCauseOfReturnId(productReturn.getCauseOfReturn().getCauseOfReturnId());

        return dto;
    }

    public List<ProductReturnResponseDto> toDtoList(List<ProductReturn> productReturns) {
        return productReturns.stream()
                .map(this::toDto)
                .toList();
    }

}
