package com.example.shopberry.domain.categories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    Category findByCategoryName(String categoryName);

    List<Category> findAllByParentCategory_CategoryId(Long parentCategoryId);

}
