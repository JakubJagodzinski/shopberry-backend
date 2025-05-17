package com.example.shopberry.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {

    private Long customerId;

    private String email;

    private LocalDateTime createdAt;

    private Boolean isCompany;

}
