package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.OrderMessages;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.orders.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerOrderOwner(Order order) {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return order.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanReadOrder(Order order) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerOrderOwnerRequest = isCustomerOrderOwner(order);

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerOrderOwnerRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

    public void checkCanReadCustomerAllOrders(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(OrderMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ORDER);
        }
    }

    public void checkCanCreateOrder(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

}
