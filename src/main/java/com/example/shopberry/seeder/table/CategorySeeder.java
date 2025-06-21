package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CategorySeeder implements DataSeeder {

    private final CategoryRepository categoryRepository;

    private static final String CATEGORY_DATA_FILE_PATH = DATA_DIRECTORY + "/categories.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (categoryRepository.count() > 0) return;

        List<String[]> rows = CsvUtils.loadFromCsv(
                CATEGORY_DATA_FILE_PATH,
                2,
                parts -> {
                    String categoryName = parts[0].trim().replaceAll("^\"|\"$", "");
                    String parentNameRaw = parts.length > 1 ? parts[1].trim() : "";
                    String parentName = parentNameRaw.equals("\"\"") || parentNameRaw.isEmpty() ? null : parentNameRaw.replaceAll("^\"|\"$", "");
                    return new String[]{categoryName, parentName};
                }
        );

        Map<String, String> categoryToParent = new HashMap<>();
        for (String[] row : rows) {
            categoryToParent.put(row[0], row[1]);
        }

        // 1. Insert all categories without parents
        Map<String, Category> categoryMap = new HashMap<>();
        for (String categoryName : categoryToParent.keySet()) {
            Category category = new Category();
            category.setCategoryName(categoryName);
            categoryMap.put(categoryName, category);
        }
        categoryRepository.saveAll(categoryMap.values());

        // 2. Set parent relations and update
        for (Map.Entry<String, String> entry : categoryToParent.entrySet()) {
            String categoryName = entry.getKey();
            String parentName = entry.getValue();

            if (parentName != null) {
                Category category = categoryMap.get(categoryName);
                Category parent = categoryMap.get(parentName);
                if (parent != null) {
                    category.setParentCategory(parent);
                }
            }
        }
        categoryRepository.saveAll(categoryMap.values());
    }


}
