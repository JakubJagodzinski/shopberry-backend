package com.example.shopberry.domain.favouriteproducts;

import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.ProductMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.favouriteproducts.dto.AddProductToFavouritesRequestDto;
import com.example.shopberry.domain.favouriteproducts.dto.FavouriteProductDtoMapper;
import com.example.shopberry.domain.favouriteproducts.dto.FavouriteProductResponseDto;
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
public class FavouriteProductService {

    private final FavouriteProductRepository favouriteProductRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final FavouriteProductDtoMapper favouriteProductDtoMapper;

    @Transactional
    public List<FavouriteProductResponseDto> getCustomerAllFavourites(UUID customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        return favouriteProductDtoMapper.toDtoList(favouriteProductRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public FavouriteProductResponseDto addProductToCustomerFavourites(UUID customerId, AddProductToFavouritesRequestDto addProductToFavouritesRequestDto) throws EntityNotFoundException, EntityExistsException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        Product product = productRepository.findById(addProductToFavouritesRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        FavouriteProductId favouriteProductId = new FavouriteProductId(customerId, addProductToFavouritesRequestDto.getProductId());

        if (favouriteProductRepository.existsById(favouriteProductId)) {
            throw new EntityExistsException(ProductMessages.PRODUCT_ALREADY_IN_FAVOURITES);
        }

        FavouriteProduct favouriteProduct = new FavouriteProduct();

        favouriteProduct.setId(favouriteProductId);
        favouriteProduct.setCustomer(customer);
        favouriteProduct.setProduct(product);

        return favouriteProductDtoMapper.toDto(favouriteProductRepository.save(favouriteProduct));
    }

    @Transactional
    public void removeProductFromCustomerFavourites(UUID customerId, Long productId) throws EntityNotFoundException {
        FavouriteProductId favouriteProductId = new FavouriteProductId(customerId, productId);

        if (!favouriteProductRepository.existsById(favouriteProductId)) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_IN_FAVOURITES);
        }

        favouriteProductRepository.deleteById(favouriteProductId);
    }

}
