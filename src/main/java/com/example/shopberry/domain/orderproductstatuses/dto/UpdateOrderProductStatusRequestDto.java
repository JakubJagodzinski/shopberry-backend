package com.example.shopberry.domain.orderproductstatuses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderProductStatusRequestDto {

    private String statusName;

}
