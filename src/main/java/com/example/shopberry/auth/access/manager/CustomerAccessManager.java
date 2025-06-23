package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.customers.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanUpdateCustomer(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isCustomerSelfRequest = customer.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanDeleteCustomer(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isCustomerSelfRequest = customer.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

}
