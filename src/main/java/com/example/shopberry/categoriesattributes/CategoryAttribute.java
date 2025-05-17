package com.example.shopberry.categoriesattributes;

import com.example.shopberry.attributes.Attribute;
import com.example.shopberry.categories.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "category_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
