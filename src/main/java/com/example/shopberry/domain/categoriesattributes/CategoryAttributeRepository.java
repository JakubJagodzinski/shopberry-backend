package com.example.shopberry.domain.categoriesattributes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, CategoryAttributeId> {

    List<CategoryAttribute> findAllByCategory_CategoryId(Long categoryId);

}
