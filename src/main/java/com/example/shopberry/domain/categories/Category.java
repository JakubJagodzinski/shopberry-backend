package com.example.shopberry.domain.categories;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", foreignKey = @ForeignKey(name = "fk_categories_parent_category"))
    private Category parentCategory = null;

    @Column(name = "leaf", nullable = false)
    private boolean isLeaf;

    @PrePersist
    protected void onCreate() {
        isLeaf = true;
    }

}
