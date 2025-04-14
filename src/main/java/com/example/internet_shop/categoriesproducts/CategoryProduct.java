package com.example.internet_shop.categoriesproducts;

import com.example.internet_shop.categories.Category;
import com.example.internet_shop.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "categories_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProduct {

    @EmbeddedId
    private CategoryProductId id;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_category_product_category"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_category_product_product"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

}
