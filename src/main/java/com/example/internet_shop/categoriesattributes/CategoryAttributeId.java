package com.example.internet_shop.categoriesattributes;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class CategoryAttributeId {

    private Long categoryId;

    private Long attributeId;

}
