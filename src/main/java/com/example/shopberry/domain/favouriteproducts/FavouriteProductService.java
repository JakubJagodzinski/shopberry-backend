package com.example.shopberry.domain.favouriteproducts;

import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.favouriteproducts.dto.AddProductToFavouritesRequestDto;
import com.example.shopberry.domain.favouriteproducts.dto.FavouriteProductDtoMapper;
import com.example.shopberry.domain.favouriteproducts.dto.FavouriteProductResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteProductService {

    private final FavouriteProductRepository favouriteProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final FavouriteProductDtoMapper favouriteProductDtoMapper;

    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private static final String PRODUCT_IS_NOT_IN_FAVOURITES = "Product is not in favourites";
    private static final String PRODUCT_IS_ALREADY_IN_FAVOURITES = "Product is already in favourites";

    @Transactional
    public List<FavouriteProductResponseDto> getCustomerAllFavourites(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return favouriteProductDtoMapper.toDtoList(favouriteProductRepository.findById_CustomerId(customerId));
    }

    @Transactional
    public FavouriteProductResponseDto addProductToCustomerFavourites(Long customerId, AddProductToFavouritesRequestDto addProductToFavouritesRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(addProductToFavouritesRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        FavouriteProductId favouriteProductId = new FavouriteProductId(customerId, addProductToFavouritesRequestDto.getProductId());

        if (favouriteProductRepository.existsById(favouriteProductId)) {
            throw new IllegalArgumentException(PRODUCT_IS_ALREADY_IN_FAVOURITES);
        }

        FavouriteProduct favouriteProduct = new FavouriteProduct();

        favouriteProduct.setId(favouriteProductId);
        favouriteProduct.setCustomer(customer);
        favouriteProduct.setProduct(product);

        return favouriteProductDtoMapper.toDto(favouriteProductRepository.save(favouriteProduct));
    }

    @Transactional
    public void removeProductFromCustomerFavourites(Long customerId, Long productId) throws EntityNotFoundException {
        FavouriteProductId favouriteProductId = new FavouriteProductId(customerId, productId);

        if (!favouriteProductRepository.existsById(favouriteProductId)) {
            throw new EntityNotFoundException(PRODUCT_IS_NOT_IN_FAVOURITES);
        }

        favouriteProductRepository.deleteById(favouriteProductId);
    }

}
