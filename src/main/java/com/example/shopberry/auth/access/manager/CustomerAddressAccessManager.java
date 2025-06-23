package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.customeraddresses.CustomerAddress;
import com.example.shopberry.domain.customers.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerAddressAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerAddressOwner(CustomerAddress customerAddress) {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return customerAddress.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanReadCustomerAllAddresses(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isCustomerSelfRequest = customer.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanCreateCustomerAddress(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = customer.getUserId().equals(currentUserId);

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanUpdateCustomerAddress(CustomerAddress customerAddress) throws AccessDeniedException {
        boolean isCustomerAddressOwnerRequest = isCustomerAddressOwner(customerAddress);

        if (!isCustomerAddressOwnerRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanDeleteCustomerAddress(CustomerAddress customerAddress) throws AccessDeniedException {
        boolean isCustomerAddressOwnerRequest = isCustomerAddressOwner(customerAddress);

        if (!isCustomerAddressOwnerRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanDeleteCustomerAllAddresses(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = customer.getUserId().equals(currentUserId);

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

}
