package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.CartProductMessages;
import com.example.shopberry.domain.cartproducts.CartProduct;
import com.example.shopberry.domain.customers.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartProductAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerCartProductOwner(CartProduct cartProduct) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return cartProduct.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanReadCustomerAllCartProduct(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(CartProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_CART);
        }
    }

    public void checkCanReadCartProduct(CartProduct cartProduct) throws AccessDeniedException {
        boolean isCustomerCartProductOwnerRequest = isCustomerCartProductOwner(cartProduct);

        if (!isCustomerCartProductOwnerRequest) {
            throw new AccessDeniedException(CartProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_CART);
        }
    }

    public void checkCanAddProductToCart(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(CartProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_CART);
        }
    }

    public void checkCanUpdateCartProduct(CartProduct cartProduct) throws AccessDeniedException {
        boolean isCustomerCartProductOwnerRequest = isCustomerCartProductOwner(cartProduct);

        if (!isCustomerCartProductOwnerRequest) {
            throw new AccessDeniedException(CartProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_CART);
        }
    }

    public void checkCanRemoveProductFromCart(CartProduct cartProduct) throws AccessDeniedException {
        boolean isCustomerCartProductOwnerRequest = isCustomerCartProductOwner(cartProduct);

        if (!isCustomerCartProductOwnerRequest) {
            throw new AccessDeniedException(CartProductMessages.YOU_HAVE_NO_ACCESS_TO_THIS_CUSTOMER_CART);
        }
    }

}
