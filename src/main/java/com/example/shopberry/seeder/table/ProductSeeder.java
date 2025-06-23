package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.categories.Category;
import com.example.shopberry.domain.categories.CategoryRepository;
import com.example.shopberry.domain.producers.Producer;
import com.example.shopberry.domain.producers.ProducerRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.utils.DoubleParser;
import com.example.shopberry.utils.ImageBase64Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements DataSeeder {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProducerRepository producerRepository;

    private static final String PRODUCT_CSV_PATH = DATA_DIRECTORY + "/products.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (productRepository.count() == 0) {
            List<Product> products = CsvUtils.loadFromCsv(
                    PRODUCT_CSV_PATH,
                    7,
                    parts -> {
                        String productName = parts[1].trim();
                        double productPrice = Double.parseDouble(parts[2].trim());
                        String producerName = parts[3].trim();
                        String categoryName = parts[4].trim();
                        String discountStr = parts[5].trim();
                        String imagePath = parts[6].trim();

                        Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
                        if (category == null) {
                            return null;
                        }

                        if (categoryRepository.existsByParentCategory_CategoryId(category.getCategoryId())) {
                            return null;
                        }

                        Producer producer = producerRepository.findByProducerName(producerName).orElse(null);

                        Product product = new Product();

                        product.setProductName(productName);
                        product.setProductPrice(productPrice);
                        product.setProducer(producer);
                        product.setCategory(category);
                        product.setDiscountPercentValue(DoubleParser.parse(discountStr));
                        if (!imagePath.isEmpty()) {
                            product.setImage(ImageBase64Utils.readImageAsBytes(imagePath));
                        }

                        return product;
                    }
            );

            products.removeIf(Objects::isNull);

            productRepository.saveAll(products);
        }
    }

}
