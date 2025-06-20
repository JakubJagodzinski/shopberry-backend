package com.example.shopberry.domain.productreturns;

import com.example.shopberry.common.constants.messages.CauseOfReturnMessages;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.common.constants.messages.ProductReturnMessages;
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
import jakarta.persistence.EntityExistsException;
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

    @Transactional
    public ProductReturnResponseDto getProductReturnById(Long productReturnId) throws EntityNotFoundException {
        ProductReturn productReturn = productReturnRepository.findById(productReturnId).orElse(null);

        if (productReturn == null) {
            throw new EntityNotFoundException(ProductReturnMessages.PRODUCT_RETURN_NOT_FOUND);
        }

        return productReturnDtoMapper.toDto(productReturn);
    }

    @Transactional
    public List<ProductReturnResponseDto> getOrderAllProductReturns(Long orderId) throws EntityNotFoundException {
        if (!orderRepository.existsById(orderId)) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        return productReturnDtoMapper.toDtoList(productReturnRepository.findAllByOrder_OrderId(orderId));
    }

    @Transactional
    public ProductReturnResponseDto createProductReturn(Long orderId, CreateProductReturnRequestDto createProductReturnRequestDto) throws EntityNotFoundException, EntityExistsException, IllegalArgumentException {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new EntityNotFoundException(OrderMessages.ORDER_NOT_FOUND);
        }

        Product product = productRepository.findById(createProductReturnRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        OrderProductId orderProductId = new OrderProductId(orderId, product.getProductId());

        if (!orderProductRepository.existsById(orderProductId)) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_DOES_NOT_BELONG_TO_THAT_ORDER);
        }

        CauseOfReturn causeOfReturn = causeOfReturnRepository.findById(createProductReturnRequestDto.getCauseOfReturnId()).orElse(null);

        if (causeOfReturn == null) {
            throw new EntityNotFoundException(CauseOfReturnMessages.CAUSE_OF_RETURN_NOT_FOUND);
        }

        if (productReturnRepository.existsByOrder_OrderIdAndProduct_ProductId(orderId, product.getProductId())) {
            throw new EntityExistsException(ProductReturnMessages.PRODUCT_RETURN_ALREADY_EXISTS);
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
            throw new EntityNotFoundException(ProductReturnMessages.PRODUCT_RETURN_NOT_FOUND);
        }

        productReturnRepository.deleteById(productReturnId);
    }

}
