package com.example.shopberry.domain.favouriteproducts;

import com.example.shopberry.auth.access.manager.FavouriteProductAccessManager;
import com.example.shopberry.common.constants.messages.CustomerMessages;
import com.example.shopberry.common.constants.messages.FavouriteProductMessages;
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

    private final FavouriteProductAccessManager favouriteProductAccessManager;

    @Transactional
    public List<FavouriteProductResponseDto> getCustomerAllFavourites(UUID customerId) throws EntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        favouriteProductAccessManager.checkCanReadCustomerAllFavouriteProducts(customer);

        return favouriteProductDtoMapper.toDtoList(favouriteProductRepository.findAllByCustomer_UserId(customerId));
    }

    @Transactional
    public FavouriteProductResponseDto addProductToCustomerFavourites(UUID customerId, AddProductToFavouritesRequestDto addProductToFavouritesRequestDto) throws EntityNotFoundException, EntityExistsException {
        Customer customer = customerRepository.findById(customerId).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CustomerMessages.CUSTOMER_NOT_FOUND);
        }

        favouriteProductAccessManager.checkCanAddProductToFavourites(customer);

        Product product = productRepository.findById(addProductToFavouritesRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(ProductMessages.PRODUCT_NOT_FOUND);
        }

        FavouriteProductId favouriteProductId = new FavouriteProductId(customerId, addProductToFavouritesRequestDto.getProductId());

        if (favouriteProductRepository.existsById(favouriteProductId)) {
            throw new EntityExistsException(FavouriteProductMessages.PRODUCT_ALREADY_IN_FAVOURITES);
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

        FavouriteProduct favouriteProduct = favouriteProductRepository.findById(favouriteProductId).orElse(null);

        if (favouriteProduct == null) {
            throw new EntityNotFoundException(FavouriteProductMessages.PRODUCT_NOT_IN_FAVOURITES);
        }

        favouriteProductAccessManager.checkCanRemoveProductFromFavourites(favouriteProduct);

        favouriteProductRepository.deleteById(favouriteProductId);
    }

}
