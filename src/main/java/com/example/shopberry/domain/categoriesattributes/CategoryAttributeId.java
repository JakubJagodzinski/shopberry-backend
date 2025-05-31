package com.example.shopberry.domain.categoriesattributes;

import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CategoryAttributeId {

    private Long categoryId;

    private Long attributeId;

}
