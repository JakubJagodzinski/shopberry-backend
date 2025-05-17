package com.example.shopberry.productreturns;

import com.example.shopberry.causesofreturn.CauseOfReturn;
import com.example.shopberry.causesofreturn.CauseOfReturnRepository;
import com.example.shopberry.orderproducts.OrderProductId;
import com.example.shopberry.orderproducts.OrderProductRepository;
import com.example.shopberry.orders.Order;
import com.example.shopberry.orders.OrderRepository;
import com.example.shopberry.productreturns.dto.CreateProductReturnRequestDto;
import com.example.shopberry.productreturns.dto.ProductReturnDtoMapper;
import com.example.shopberry.productreturns.dto.ProductReturnResponseDto;
import com.example.shopberry.products.Product;
import com.example.shopberry.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReturnService {

    private final ProductReturnRepository productReturnRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CauseOfReturnRepository causeOfReturnRepository;
    private final OrderProductRepository orderProductRepository;

    private final ProductReturnDtoMapper productReturnDtoMapper;

    private final String PRODUCT_RETURN_NOT_FOUND_MESSAGE = "Product return not found";
    private final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String CAUSE_OF_RETURN_NOT_FOUND_MESSAGE = "Cause of return not found";
    private final String PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE = "Product does not belong to that order";
    private final String PRODUCT_RETURN_ALREADY_EXISTS_MESSAGE = "Product return already exists";

    public ProductReturnService(ProductReturnRepository productReturnRepository, OrderRepository orderRepository, ProductRepository productRepository, CauseOfReturnRepository causeOfReturnRepository, OrderProductRepository orderProductRepository, ProductReturnDtoMapper productReturnDtoMapper) {
        this.productReturnRepository = productReturnRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.causeOfReturnRepository = causeOfReturnRepository;
        this.orderProductRepository = orderProductRepository;
        this.productReturnDtoMapper = productReturnDtoMapper;
    }

    @Transactional
    public ProductReturnResponseDto getProductReturnById(Long productReturnId) throws EntityNotFoundException {
        ProductReturn productReturn = productReturnRepository.findById(productReturnId).orElse(null);

        if (productReturn == null) {
            throw new EntityNotFoundException(PRODUCT_RETURN_NOT_FOUND_MESSAGE);
        }

        return productReturnDtoMapper.toDto(productReturn);
    }

    @Transactional
    public List<ProductReturnResponseDto> getProductReturnsByOrderId(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        return productReturnDtoMapper.toDtoList(productReturnRepository.findByOrder_OrderId(orderId));
    }

    @Transactional
    public ProductReturnResponseDto createProductReturn(CreateProductReturnRequestDto createProductReturnRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Order order = orderRepository.findById(createProductReturnRequestDto.getOrderId()).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(ORDER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createProductReturnRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new IllegalArgumentException(PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER_MESSAGE);
        }

        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(createProductReturnRequestDto.getCauseOfReturnId()).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CAUSE_OF_RETURN_NOT_FOUND_MESSAGE);
        }

        if (productReturnRepository.existsByOrder_OrderIdAndProduct_ProductId(order.getOrderId(), product.getProductId())) {
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
