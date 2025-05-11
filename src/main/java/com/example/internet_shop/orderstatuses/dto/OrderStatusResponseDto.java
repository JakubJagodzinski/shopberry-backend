package com.example.internet_shop.orderstatuses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusResponseDto {

    private Long orderStatusId;

    private String orderStatusName;

}
