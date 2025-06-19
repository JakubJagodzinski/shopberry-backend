package com.example.shopberry.domain.cartentries;

import com.example.shopberry.domain.cartentries.dto.AddProductToCustomerCartRequestDto;
import com.example.shopberry.domain.cartentries.dto.CartEntryDtoMapper;
import com.example.shopberry.domain.cartentries.dto.CartEntryResponseDto;
import com.example.shopberry.domain.cartentries.dto.UpdateCartEntryRequestDto;
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
public class CartEntryService {

    private final CartEntryRepository cartEntryRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final CartEntryDtoMapper cartEntryDtoMapper;

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String CART_ENTRY_NOT_FOUND_MESSAGE = "Cart entry not found";
    private static final String QUANTITY_MUST_BE_POSITIVE_MESSAGE = "Quantity must be positive";
    private static final String CART_ENTRY_ALREADY_EXISTS_MESSAGE = "Cart entry already exists";

    @Transactional
    public CartEntryResponseDto getCustomerCartProduct(Long customerId, Long productId) throws EntityNotFoundException {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElse(null);

        if (cartEntry == null) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDto(cartEntry);
    }

    @Transactional
    public List<CartEntryResponseDto> getCustomerAllCartProducts(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDtoList(cartEntryRepository.findByCustomer_Id(customerId));
    }

    @Transactional
    public CartEntryResponseDto addProductToCustomerCart(Long customerId, AddProductToCustomerCartRequestDto addProductToCustomerCartRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(addProductToCustomerCartRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        CartEntryId cartEntryId = new CartEntryId(customerId, addProductToCustomerCartRequestDto.getProductId());

        if (cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_ALREADY_EXISTS_MESSAGE);
        }

        CartEntry cartEntry = new CartEntry();

        cartEntry.setId(cartEntryId);
        cartEntry.setCustomer(customer);
        cartEntry.setProduct(product);

        if (addProductToCustomerCartRequestDto.getQuantity() <= 0) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
        }
        cartEntry.setQuantity(addProductToCustomerCartRequestDto.getQuantity());

        return cartEntryDtoMapper.toDto(cartEntryRepository.save(cartEntry));
    }

    @Transactional
    public CartEntryResponseDto updateCustomerCartProduct(Long customerId, Long productId, UpdateCartEntryRequestDto updateCartEntryRequestDto) throws EntityNotFoundException {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElse(null);

        if (cartEntry == null) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        if (updateCartEntryRequestDto.getQuantity() != null) {
            if (updateCartEntryRequestDto.getQuantity() <= 0) {
                throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
            }

            cartEntry.setQuantity(updateCartEntryRequestDto.getQuantity());
        }

        return cartEntryDtoMapper.toDto(cartEntryRepository.save(cartEntry));
    }

    @Transactional
    public void removeProductFromCustomerCart(Long customerId, Long productId) throws EntityNotFoundException {
        CartEntryId cartEntryId = new CartEntryId(customerId, productId);

        if (!cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        cartEntryRepository.deleteById(cartEntryId);
    }

}
