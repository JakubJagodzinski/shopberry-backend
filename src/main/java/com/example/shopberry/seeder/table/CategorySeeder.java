package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CategorySeeder implements DataSeeder {

    private final CategoryRepository categoryRepository;

    private static final String CATEGORY_DATA_FILE_PATH = DATA_DIRECTORY + "/categories.csv";

    @Override
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

        Set<String> created = new HashSet<>();

        while (created.size() < categoryToParent.size()) {
            for (Map.Entry<String, String> entry : categoryToParent.entrySet()) {
                String categoryName = entry.getKey();
                String parentName = entry.getValue();

                if (created.contains(categoryName)) continue;

                if (parentName == null || created.contains(parentName)) {
                    Category category = new Category();
                    category.setCategoryName(categoryName);

                    if (parentName != null) {
                        Category parent = categoryRepository.findByCategoryName(parentName).orElse(null);
                        if (parent != null && !parent.equals(category)) {
                            category.setParentCategory(parent);
                        }
                    }

                    categoryRepository.save(category);
                    created.add(categoryName);
                }
            }
        }
    }

}
