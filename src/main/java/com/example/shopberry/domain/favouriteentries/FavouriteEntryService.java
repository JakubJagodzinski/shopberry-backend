package com.example.shopberry.domain.favouriteentries;

import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.customers.CustomerRepository;
import com.example.shopberry.domain.favouriteentries.dto.CreateFavouriteEntryRequestDto;
import com.example.shopberry.domain.favouriteentries.dto.FavouriteEntryDtoMapper;
import com.example.shopberry.domain.favouriteentries.dto.FavouriteEntryResponseDto;
import com.example.shopberry.domain.products.Product;
import com.example.shopberry.domain.products.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteEntryService {

    private final FavouriteEntryRepository favouriteEntryRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    private final FavouriteEntryDtoMapper favouriteEntryDtoMapper;

    private final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";
    private final String PRODUCT_NOT_FOUND_MESSAGE = "Product not found";
    private final String FAVOURITE_ENTRY_NOT_FOUND_MESSAGE = "Favourite entry not found";
    private final String FAVOURITE_ENTRY_ALREADY_EXISTS_MESSAGE = "Favourite entry already exists";

    @Transactional
    public List<FavouriteEntryResponseDto> getFavouriteEntriesByCustomerId(Long customerId) throws EntityNotFoundException {
        if (!customerRepository.existsById(customerId)) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        return favouriteEntryDtoMapper.toDtoList(favouriteEntryRepository.findById_CustomerId(customerId));
    }

    @Transactional
    public FavouriteEntryResponseDto createFavouriteEntry(CreateFavouriteEntryRequestDto createFavouriteEntryRequestDto) throws EntityNotFoundException, IllegalArgumentException {
        Customer customer = customerRepository.findById(createFavouriteEntryRequestDto.getCustomerId()).orElse(null);

        if (customer == null) {
            throw new EntityNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE);
        }

        Product product = productRepository.findById(createFavouriteEntryRequestDto.getProductId()).orElse(null);

        if (product == null) {
            throw new EntityNotFoundException(PRODUCT_NOT_FOUND_MESSAGE);
        }

        FavouriteEntryId favouriteEntryId = new FavouriteEntryId(createFavouriteEntryRequestDto.getCustomerId(), createFavouriteEntryRequestDto.getProductId());

        if (favouriteEntryRepository.existsById(favouriteEntryId)) {
            throw new IllegalArgumentException(FAVOURITE_ENTRY_ALREADY_EXISTS_MESSAGE);
        }

        FavouriteEntry favouriteEntry = new FavouriteEntry();

        favouriteEntry.setId(favouriteEntryId);
        favouriteEntry.setCustomer(customer);
        favouriteEntry.setProduct(product);

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
