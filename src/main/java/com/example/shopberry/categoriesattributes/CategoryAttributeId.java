package com.example.shopberry.categoriesattributes;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAttributeId {

    private Long categoryId;

    private Long attributeId;

}
