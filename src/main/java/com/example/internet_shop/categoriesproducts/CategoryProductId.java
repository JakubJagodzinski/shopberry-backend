package com.example.internet_shop.categoriesproducts;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProductId {

    private Long categoryId;

    private Long productId;

}
