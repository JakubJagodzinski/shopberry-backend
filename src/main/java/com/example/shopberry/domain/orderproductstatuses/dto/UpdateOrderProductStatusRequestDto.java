package com.example.shopberry.domain.orderproductstatuses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderProductStatusRequestDto {

    @JsonProperty("status_name")
    private String statusName;

}
