package com.example.shopberry.orderproductstatuses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductStatusResponseDto {

    private Long orderProductStatusId;

    private String statusName;

}
