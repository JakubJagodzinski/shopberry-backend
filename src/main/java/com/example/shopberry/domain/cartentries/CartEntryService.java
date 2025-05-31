package com.example.shopberry.domain.cartentries;

import com.example.shopberry.domain.cartentries.dto.CartEntryDtoMapper;
import com.example.shopberry.domain.cartentries.dto.CartEntryResponseDto;
import com.example.shopberry.domain.cartentries.dto.CreateCartEntryRequestDto;
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

    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String CART_ENTRY_NOT_FOUND_MESSAGE = "Cart entry not found";
    private final String QUANTITY_MUST_BE_POSITIVE_MESSAGE = "Quantity must be positive";
    private final String CART_ENTRY_ALREADY_EXISTS_MESSAGE = "Cart entry already exists";

    @Transactional
    public CartEntryResponseDto getCartEntryByCartEntryId(CartEntryId cartEntryId) throws EntityNotFoundException {
        CartEntry cartEntry = cartEntryRepository.findById(cartEntryId).orElse(null);

        if (cartEntry == null) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDto(cartEntry);
    }

    @Transactional
    public List<CartEntryResponseDto> getCartEntriesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDtoList(cartEntryRepository.findByCustomer_Id(customerId));
    }

    @Transactional
    public CartEntryResponseDto createCartEntry(CreateCartEntryRequestDto createCartEntryRequestDto) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(createCartEntryRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createCartEntryRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        CartEntryId cartEntryId = new CartEntryId(createCartEntryRequestDto.getCustomerId(), createCartEntryRequestDto.getProductId());

        if (cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_ALREADY_EXISTS_MESSAGE);
        }

        CartEntry cartEntry = new CartEntry();

        cartEntry.setId(cartEntryId);
        cartEntry.setCustomer(customer);
        cartEntry.setProduct(product);

        if (createCartEntryRequestDto.getQuantity() <= 0) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
        }
        cartEntry.setQuantity(createCartEntryRequestDto.getQuantity());

        return cartEntryDtoMapper.toDto(cartEntryRepository.save(cartEntry));
    }

    @Transactional
    public CartEntryResponseDto updateCartEntryByCartEntryId(CartEntryId cartEntryId, UpdateCartEntryRequestDto updateCartEntryRequestDto) throws EntityNotFoundException {
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
    public void deleteCartEntryByCartEntryId(CartEntryId cartEntryId) throws EntityNotFoundException {
        if (!cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        cartEntryRepository.deleteById(cartEntryId);
    }

}
