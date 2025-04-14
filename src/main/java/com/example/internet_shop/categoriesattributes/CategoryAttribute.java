package com.example.internet_shop.categoriesattributes;

import com.example.internet_shop.attributes.Attribute;
import com.example.internet_shop.categories.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "category_attributes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryAttribute {

    @EmbeddedId
    private CategoryAttributeId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id", foreignKey = @ForeignKey(name = "fk_attribute"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attribute attribute;

}
