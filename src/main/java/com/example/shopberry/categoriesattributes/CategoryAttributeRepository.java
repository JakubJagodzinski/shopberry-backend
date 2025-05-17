package com.example.shopberry.categoriesattributes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, CategoryAttributeId> {

    List<CategoryAttribute> findByCategory_CategoryId(Long categoryId);

}
