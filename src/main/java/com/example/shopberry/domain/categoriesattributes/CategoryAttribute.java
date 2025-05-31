package com.example.shopberry.domain.categoriesattributes;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.categories.Category;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "category_attributes")
public class CategoryAttribute {

    @EmbeddedId
    private CategoryAttributeId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_category_attribute_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", foreignKey = @ForeignKey(name = "fk_category_attribute_attribute"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attribute attribute;

}
