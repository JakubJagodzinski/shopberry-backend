package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.FavouriteProductMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.favouriteproducts.FavouriteProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FavouriteProductAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerFavouriteProductOwner(FavouriteProduct favouriteProduct) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return favouriteProduct.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanReadCustomerAllFavouriteProducts(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(FavouriteProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_FAVOURITE_PRODUCTS);
        }
    }

    public void checkCanAddProductToFavourites(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(FavouriteProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_FAVOURITE_PRODUCTS);
        }
    }

    public void checkCanRemoveProductFromFavourites(FavouriteProduct favouriteProduct) throws AccessDeniedException {
        boolean isCustomerFavouriteProductOwnerRequest = isCustomerFavouriteProductOwner(favouriteProduct);

        if (!isCustomerFavouriteProductOwnerRequest) {
            throw new AccessDeniedException(FavouriteProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_FAVOURITE_PRODUCTS);
        }
    }

}
