package com.example.internet_shop.carts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCartEntryDto {

    private Long customerId;

    private Long productId;

    private Long quantity;

}
