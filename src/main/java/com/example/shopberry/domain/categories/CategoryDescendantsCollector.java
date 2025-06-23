package com.example.shopberry.domain.categories;

import java.util.ArrayList;
import java.util.List;

public class CategoryDescendantsCollector {

    public static List<Category> collectDescendants(List<Category> allCategories, Long rootId) {
        List<Category> result = new ArrayList<>();
        Category root = null;

        for (Category category : allCategories) {
            if (category.getCategoryId().equals(rootId)) {
                root = category;
                break;
            }
        }

        if (root != null) {
            collectChildrenRecursively(root, allCategories, result);
        }

        return result;
    }

    private static void collectChildrenRecursively(Category parent, List<Category> all, List<Category> result) {
        for (Category category : all) {
            if (category.getParentCategory() != null && category.getParentCategory().getCategoryId().equals(parent.getCategoryId())) {
                result.add(category);
                collectChildrenRecursively(category, all, result);
            }
        }
    }

}
