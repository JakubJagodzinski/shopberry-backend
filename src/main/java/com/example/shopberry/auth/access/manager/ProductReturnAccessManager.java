package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.orders.Order;
import com.example.shopberry.domain.productreturns.ProductReturn;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductReturnAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerOrderOwner(Order order) {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return order.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanReadProductReturn(ProductReturn productReturn) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerOrderOwnerRequest = isCustomerOrderOwner(productReturn.getOrder());

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerOrderOwnerRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

    public void checkCanReadOrderAllProductReturns(Order order) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerOrderOwnerRequest = isCustomerOrderOwner(order);

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerOrderOwnerRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

    public void checkCanCreateProductReturn(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

    public void checkCanDeleteProductReturn(ProductReturn productReturn) throws AccessDeniedException {
        boolean isCustomerOrderOwnerRequest = isCustomerOrderOwner(productReturn.getOrder());

        if (!isCustomerOrderOwnerRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

}
