package com.example.shopberry.domain.complaints.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplaintRequestDto {

    private Long orderId;

    private Long productId;

    private String info;

    private String firstName;

    private String lastName;

    private String nip;

    private String city;

    private String postalCode;

    private String street;

    private String houseNumber;

    private String apartment;

    private String phoneNumber;

}
