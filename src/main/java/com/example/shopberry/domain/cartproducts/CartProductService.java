package com.example.shopberry.domain.cartproducts;

import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.cartproducts.dto.AddProductToCartRequestDto;
import com.example.shopberry.domain.cartproducts.dto.CartProductDtoMapper;
import com.example.shopberry.domain.cartproducts.dto.CartProductResponseDto;
import com.example.shopberry.domain.cartproducts.dto.UpdateCartProductRequestDto;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final CartProductDtoMapper cartProductDtoMapper;

    @Transactional
    public CartProductResponseDto getCustomerCartProduct(UUID customerId, Long productId) throws EntityNotFoundException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);

        if (cartProduct == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_IN_CART);
        }

        return cartProductDtoMapper.toDto(cartProduct);
    }

    @Transactional
    public List<CartProductResponseDto> getCustomerAllCartProducts(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        return cartProductDtoMapper.toDtoList(cartProductRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public CartProductResponseDto addProductToCustomerCart(UUID customerId, AddProductToCartRequestDto addProductToCartRequestDto) throws EntityNotFoundException, EntityExistsException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        Product product = productRepository.findById(addProductToCartRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        CartProductId cartProductId = new CartProductId(customerId, addProductToCartRequestDto.getProductId());

        if (cartProductRepository.existsById(cartProductId)) {
            throw new EntityExistsException(ProductMessages.PRODUCT_ALREADY_IN_CART);
        }

        CartProduct cartProduct = new CartProduct();

        cartProduct.setId(cartProductId);
        cartProduct.setCustomer(customer);
        cartProduct.setProduct(product);

        if (addProductToCartRequestDto.getProductQuantity() <= 0) {
            throw new IllegalArgumentException(ProductMessages.PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO);
        }
        cartProduct.setProductQuantity(addProductToCartRequestDto.getProductQuantity());

        return cartProductDtoMapper.toDto(cartProductRepository.save(cartProduct));
    }

    @Transactional
    public CartProductResponseDto updateCustomerCartProduct(UUID customerId, Long productId, UpdateCartProductRequestDto updateCartProductRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);

        if (cartProduct == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_IN_CART);
        }

        if (updateCartProductRequestDto.getProductQuantity() != null) {
            if (updateCartProductRequestDto.getProductQuantity() <= 0) {
                throw new IllegalArgumentException(ProductMessages.PRODUCT_QUANTITY_MUST_BE_GREATER_THAN_ZERO);
            }

            cartProduct.setProductQuantity(updateCartProductRequestDto.getProductQuantity());
        }

        return cartProductDtoMapper.toDto(cartProductRepository.save(cartProduct));
    }

    @Transactional
    public void removeProductFromCustomerCart(UUID customerId, Long productId) throws EntityNotFoundException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        if (!cartProductRepository.existsById(cartProductId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_IN_CART);
        }

        cartProductRepository.deleteById(cartProductId);
    }

}
