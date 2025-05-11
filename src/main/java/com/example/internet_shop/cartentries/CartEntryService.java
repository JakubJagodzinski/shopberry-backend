package com.example.internet_shop.cartentries;

import com.example.internet_shop.cartentries.dto.CartEntryResponseDto;
import com.example.internet_shop.cartentries.dto.CartEntryDtoMapper;
import com.example.internet_shop.cartentries.dto.CreateCartEntryRequestDto;
import com.example.internet_shop.cartentries.dto.UpdateCartEntryRequestDto;
import com.example.internet_shop.customers.CustomerRepository;
import com.example.internet_shop.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public CartEntryService(CartEntryRepository cartEntryRepository, CustomerRepository customerRepository, ProductRepository productRepository, CartEntryDtoMapper cartEntryDtoMapper) {
        this.cartEntryRepository = cartEntryRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.cartEntryDtoMapper = cartEntryDtoMapper;
    }

    @Transactional
    public CartEntryResponseDto getCartEntryByCartEntryId(CartEntryId cartEntryId) throws EntityNotFoundException {
        if (!cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDto(cartEntryRepository.getReferenceById(cartEntryId));
    }

    @Transactional
    public List<CartEntryResponseDto> getCartEntriesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return cartEntryDtoMapper.toDtoList(cartEntryRepository.findByCustomer_CustomerId(customerId));
    }

    @Transactional
    public CartEntryResponseDto createCartEntry(CreateCartEntryRequestDto createCartEntryRequestDto) throws EntityNotFoundException {
        if (!customerRepository.existsById(createCartEntryRequestDto.getCustomerId())) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(createCartEntryRequestDto.getProductId())) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        CartEntryId cartEntryId = new CartEntryId(createCartEntryRequestDto.getCustomerId(), createCartEntryRequestDto.getProductId());

        if (cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_ALREADY_EXISTS_MESSAGE);
        }

        CartEntry cartEntry = new CartEntry();

        cartEntry.setId(cartEntryId);
        cartEntry.setCustomer(customerRepository.getReferenceById(createCartEntryRequestDto.getCustomerId()));
        cartEntry.setProduct(productRepository.getReferenceById(createCartEntryRequestDto.getProductId()));

        if (createCartEntryRequestDto.getQuantity() <= 0) {
            throw new IllegalArgumentException(QUANTITY_MUST_BE_POSITIVE_MESSAGE);
        }
        cartEntry.setQuantity(createCartEntryRequestDto.getQuantity());

        return cartEntryDtoMapper.toDto(cartEntryRepository.save(cartEntry));
    }

    @Transactional
    public CartEntryResponseDto updateCartEntryByCartEntryId(CartEntryId cartEntryId, UpdateCartEntryRequestDto updateCartEntryRequestDto) throws EntityNotFoundException {
        if (!cartEntryRepository.existsById(cartEntryId)) {
            throw new EntityNotFoundException(CART_ENTRY_NOT_FOUND_MESSAGE);
        }

        CartEntry cartEntry = cartEntryRepository.getReferenceById(cartEntryId);

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
