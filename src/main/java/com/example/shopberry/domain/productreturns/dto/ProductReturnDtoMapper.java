package com.example.shopberry.domain.productreturns.dto;

import com.example.shopberry.domain.causesofreturn.dto.CauseOfReturnDtoMapper;
import com.example.shopberry.domain.orders.dto.OrderDtoMapper;
import com.example.shopberry.domain.productreturns.ProductReturn;
import com.example.shopberry.domain.products.dto.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductReturnDtoMapper {

    private final OrderDtoMapper orderDtoMapper;
    private final ProductDtoMapper productDtoMapper;
    private final CauseOfReturnDtoMapper causeOfReturnDtoMapper;

    public ProductReturnResponseDto toDto(ProductReturn productReturn) {
        ProductReturnResponseDto dto = new ProductReturnResponseDto();

        dto.setProductReturnId(productReturn.getProductReturnId());
        dto.setOrder(orderDtoMapper.toDto(productReturn.getOrder()));
        dto.setProduct(productDtoMapper.toDto(productReturn.getProduct()));
        dto.setCauseOfReturn(causeOfReturnDtoMapper.toDto(productReturn.getCauseOfReturn()));

        return dto;
    }

    public List<ProductReturnResponseDto> toDtoList(List<ProductReturn> productReturnList) {
        return productReturnList.stream()
                .map(this::toDto)
                .toList();
    }

}
