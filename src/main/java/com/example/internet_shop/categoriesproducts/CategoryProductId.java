package com.example.internet_shop.categoriesproducts;

import jakarta.persistence.Embeddable;

@Embeddable
public class CategoryProductId {

    private Long categoryId;

    private Long productId;

}
