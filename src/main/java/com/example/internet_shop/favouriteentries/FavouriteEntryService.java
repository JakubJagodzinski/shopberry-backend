package com.example.internet_shop.favouriteentries;

import com.example.internet_shop.customers.CustomerRepository;
import com.example.internet_shop.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteEntryService {

    private final FavouriteEntryRepository favouriteEntryRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final FavouriteEntryDtoMapper favouriteEntryDtoMapper;

    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String FAVOURITE_ENTRY_NOT_FOUND_MESSAGE = "Favourite entry not found";
    private final String FAVOURITE_ENTRY_ALREADY_EXISTS_MESSAGE = "Favourite entry already exists";

    public FavouriteEntryService(FavouriteEntryRepository favouriteEntryRepository, CustomerRepository customerRepository, ProductRepository productRepository, FavouriteEntryDtoMapper favouriteEntryDtoMapper) {
        this.favouriteEntryRepository = favouriteEntryRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.favouriteEntryDtoMapper = favouriteEntryDtoMapper;
    }

    @Transactional
    public List<FavouriteEntryDto> getFavouriteEntriesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return favouriteEntryDtoMapper.toDtoList(favouriteEntryRepository.findById_CustomerId(customerId));
    }

    @Transactional
    public FavouriteEntryDto createFavouriteEntry(CreateFavouriteEntryDto createFavouriteEntryDto) throws EntityNotFoundException, IllegalArgumentException {
        if (!customerRepository.existsById(createFavouriteEntryDto.getCustomerId())) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        if (!productRepository.existsById(createFavouriteEntryDto.getProductId())) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        FavouriteEntryId favouriteEntryId = new FavouriteEntryId(createFavouriteEntryDto.getCustomerId(), createFavouriteEntryDto.getProductId());

        if (favouriteEntryRepository.existsById(favouriteEntryId)) {
            throw new IllegalArgumentException(FAVOURITE_ENTRY_ALREADY_EXISTS_MESSAGE);
        }

        FavouriteEntry favouriteEntry = new FavouriteEntry();

        favouriteEntry.setId(favouriteEntryId);
        favouriteEntry.setCustomer(customerRepository.getReferenceById(createFavouriteEntryDto.getCustomerId()));
        favouriteEntry.setProduct(productRepository.getReferenceById(createFavouriteEntryDto.getProductId()));

        return favouriteEntryDtoMapper.toDto(favouriteEntryRepository.save(favouriteEntry));
    }

    @Transactional
    public void deleteFavouriteEntryByFavouriteEntryId(FavouriteEntryId favouriteEntryId) throws EntityNotFoundException {
        if (!favouriteEntryRepository.existsById(favouriteEntryId)) {
            throw new EntityNotFoundException(FAVOURITE_ENTRY_NOT_FOUND_MESSAGE);
        }

        favouriteEntryRepository.deleteById(favouriteEntryId);
    }

}
