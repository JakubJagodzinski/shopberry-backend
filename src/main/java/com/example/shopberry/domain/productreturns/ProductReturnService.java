package com.example.shopberry.domain.productreturns;

import com.example.shopberry.domain.causesofreturn.CauseOfReturn;
import com.example.shopberry.domain.causesofreturn.CauseOfReturnRepository;
import com.example.shopberry.domain.orderproducts.OrderProductId;
import com.example.shopberry.domain.orderproducts.OrderProductRepository;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.orders.OrderRepository;
import com.example.shopberry.domain.productreturns.dto.CreateProductReturnRequestDto;
import com.example.shopberry.domain.productreturns.dto.ProductReturnDtoMapper;
import com.example.shopberry.domain.productreturns.dto.ProductReturnResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReturnService {

    private final ProductReturnRepository productReturnRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CauseOfReturnRepository causeOfReturnRepository;
    private final OrderProductRepository orderProductRepository;

    private final ProductReturnDtoMapper productReturnDtoMapper;

    private static final String PRODUCT_RETURN_NOT_FOUND_MESSAGE = "Product return not found";
    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String CAUSE_OF_RETURN_NOT_FOUND_MESSAGE = "Cause of return not found";
    private static final String PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE = "Product does not belong to that order";
    private static final String PRODUCT_RETURN_ALREADY_EXISTS_MESSAGE = "Product return already exists";

    @Transactional
    public ProductReturnResponseDto getProductReturnById(Long productReturnId) throws EntityNotFoundException {
        ProductReturn productReturn = productReturnRepository.findById(productReturnId).orElse(null);

        if (productReturn == null) {
            throw new EntityNotFoundException(PRODUCT_RETURN_NOT_FOUND_MESSAGE);
        }

        return productReturnDtoMapper.toDto(productReturn);
    }

    @Transactional
    public List<ProductReturnResponseDto> getOrderAllProductReturns(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        return productReturnDtoMapper.toDtoList(productReturnRepository.findByOrder_OrderId(orderId));
    }

    @Transactional
    public ProductReturnResponseDto createProductReturn(Long orderId, CreateProductReturnRequestDto createProductReturnRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createProductReturnRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        OrderProductId orderProductId = new OrderProductId(orderId, product.getProductId());

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new IllegalArgumentException(PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE);
        }

        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(createProductReturnRequestDto.getCauseOfReturnId()).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        if (productReturnRepository.existsByOrder_OrderIdAndProduct_ProductId(orderId, product.getProductId())) {
            throw new IllegalArgumentException(PRODUCT_RETURN_ALREADY_EXISTS_MESSAGE);
        }

        ProductReturn productReturn = new ProductReturn();

        productReturn.setOrder(order);
        productReturn.setProduct(product);
        productReturn.setCauseOfReturn(causeOfReturn);

        return productReturnDtoMapper.toDto(productReturnRepository.save(productReturn));
    }

    @Transactional
    public void deleteProductReturnById(Long productReturnId) throws EntityNotFoundException {
        if (!productReturnRepository.existsById(productReturnId)) {
            throw new EntityNotFoundException(PRODUCT_RETURN_NOT_FOUND_MESSAGE);
        }

        productReturnRepository.deleteById(productReturnId);
    }

}
