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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
    public void seed() throws IOException {
        if (productRepository.count() == 0) {
            List<Product> products = CsvUtils.loadFromCsv(
                    PRODUCT_CSV_PATH,
                    5,
                    parts -> {
                        String productName = parts[0].trim();
                        double productPrice = Double.parseDouble(parts[1].trim());
                        String producerName = parts[2].trim();
                        String categoryName = parts[3].trim();
                        String discountStr = parts[4].trim();

                        Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
                        if (category == null) {
                            return null; // skip this product
                        }

                        Producer producer = producerName.isEmpty() ? null :
                                producerRepository.findByProducerName(producerName).orElse(null);

                        Product product = new Product();
                        product.setProductName(productName);
                        product.setProductPrice(productPrice);
                        product.setProducer(producer);
                        product.setCategory(category);
                        product.setDiscountPercentValue(DoubleParser.parse(discountStr));

                        return product;
                    }
            );

            products.removeIf(Objects::isNull);

            productRepository.saveAll(products);
        }
    }
}
