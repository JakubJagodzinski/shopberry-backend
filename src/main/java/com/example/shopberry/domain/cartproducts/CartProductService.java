package com.example.shopberry.domain.cartproducts;

import com.example.shopberry.domain.cartproducts.dto.AddProductToCartRequestDto;
import com.example.shopberry.domain.cartproducts.dto.CartProductDtoMapper;
import com.example.shopberry.domain.cartproducts.dto.CartProductResponseDto;
import com.example.shopberry.domain.cartproducts.dto.UpdateCartProductRequestDto;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final CartProductDtoMapper cartProductDtoMapper;

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_NOT_IN_CART_MESSAGE = "Product not in cart";
    private static final String QUANTITY_MUST_BE_POSITIVE_MESSAGE = "Quantity must be positive";
    private static final String PRODUCT_ALREADY_IN_CART_MESSAGE = "Product already in cart";

    @Transactional
    public CartProductResponseDto getCustomerCartProduct(Long customerId, Long productId) throws EntityNotFoundException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);

        if (cartProduct == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_IN_CART_MESSAGE);
        }

        return cartProductDtoMapper.toDto(cartProduct);
    }

    @Transactional
    public List<CartProductResponseDto> getCustomerAllCartProducts(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return cartProductDtoMapper.toDtoList(cartProductRepository.findByCustomer_Id(customerId));
    }

    @Transactional
    public CartProductResponseDto addProductToCustomerCart(Long customerId, AddProductToCartRequestDto addProductToCartRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(addProductToCartRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        CartProductId cartProductId = new CartProductId(customerId, addProductToCartRequestDto.getProductId());

        if (cartProductRepository.existsById(cartProductId)) {
            throw new EntityNotFoundException(PRODUCT_ALREADY_IN_CART_MESSAGE);
        }

        CartProduct cartProduct = new CartProduct();

        cartProduct.setId(cartProductId);
        cartProduct.setCustomer(customer);
        cartProduct.setProduct(product);

        if (addProductToCartRequestDto.getQuantity() <= 0) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
        }
        cartProduct.setQuantity(addProductToCartRequestDto.getQuantity());

        return cartProductDtoMapper.toDto(cartProductRepository.save(cartProduct));
    }

    @Transactional
    public CartProductResponseDto updateCustomerCartProduct(Long customerId, Long productId, UpdateCartProductRequestDto updateCartProductRequestDto) throws EntityNotFoundException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElse(null);

        if (cartProduct == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_IN_CART_MESSAGE);
        }

        if (updateCartProductRequestDto.getQuantity() != null) {
            if (updateCartProductRequestDto.getQuantity() <= 0) {
                throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
            }

            cartProduct.setQuantity(updateCartProductRequestDto.getQuantity());
        }

        return cartProductDtoMapper.toDto(cartProductRepository.save(cartProduct));
    }

    @Transactional
    public void removeProductFromCustomerCart(Long customerId, Long productId) throws EntityNotFoundException {
        CartProductId cartProductId = new CartProductId(customerId, productId);

        if (!cartProductRepository.existsById(cartProductId)) {
            throw new EntityNotFoundException(PRODUCT_NOT_IN_CART_MESSAGE);
        }

        cartProductRepository.deleteById(cartProductId);
    }

}
