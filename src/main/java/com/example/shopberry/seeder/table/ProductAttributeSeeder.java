package com.example.shopberry.seeder.table;

import com.example.shopberry.domain.attributes.Attribute;
import com.example.shopberry.domain.attributes.AttributeRepository;
import com.example.shopberry.domain.productattributes.ProductAttribute;
import com.example.shopberry.domain.productattributes.ProductAttributeId;
import com.example.shopberry.domain.productattributes.ProductAttributeRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import com.example.shopberry.seeder.DataSeeder;
import com.example.shopberry.utils.CsvUtils;
import com.example.shopberry.utils.DoubleParser;
import com.example.shopberry.utils.LongParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductAttributeSeeder implements DataSeeder {

    private final ProductAttributeRepository productAttributeRepository;
    private final AttributeRepository attributeRepository;
    private final ProductRepository productRepository;

    private static final String PRODUCT_ATTRIBUTES_DATA_FILE_PATH = DATA_DIRECTORY + "/product_attributes.csv";

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void seed() throws IOException {
        if (productAttributeRepository.count() == 0) {
            List<ProductAttribute> productAttributeList = CsvUtils.loadFromCsv(
                    PRODUCT_ATTRIBUTES_DATA_FILE_PATH,
                    4,
                    parts -> {
                        ProductAttribute productAttribute = new ProductAttribute();

                        Product product = productRepository.findById(LongParser.parse(parts[0].trim())).orElse(null);

                        if (product == null) {
                            return null;
                        }

                        productAttribute.setProduct(product);

                        Attribute attribute = attributeRepository.findById(LongParser.parse(parts[1].trim())).orElse(null);

                        if (attribute == null) {
                            return null;
                        }

                        ProductAttributeId productAttributeId = new ProductAttributeId(product.getProductId(), attribute.getAttributeId());

                        productAttribute.setId(productAttributeId);
                        productAttribute.setAttribute(attribute);
                        productAttribute.setValue(parts[2].trim());
                        productAttribute.setWeight(DoubleParser.parse(parts[3].trim()));

                        return productAttribute;
                    }
            );

            productAttributeRepository.saveAll(productAttributeList);
        }
    }

}
