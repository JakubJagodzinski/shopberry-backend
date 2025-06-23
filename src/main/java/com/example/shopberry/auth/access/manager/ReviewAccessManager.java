package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.ReviewMessages;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.customers.Customer;
import com.example.shopberry.domain.reviews.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReviewAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isCustomerReviewOwner(Review review) {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        return review.getCustomer().getUserId().equals(currentCustomerId);
    }

    public void checkCanCreateReview(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanUpdateReview(Review review) throws AccessDeniedException {
        boolean isCustomerReviewOwnerRequest = isCustomerReviewOwner(review);

        if (!isCustomerReviewOwnerRequest) {
            throw new AccessDeniedException(ReviewMessages.YOU_HAVE_NO_ACCESS_TO_THIS_REVIEW);
        }
    }

    public void checkCanDeleteReview(Review review) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerReviewOwnerRequest = isCustomerReviewOwner(review);

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerReviewOwnerRequest) {
            throw new AccessDeniedException(ReviewMessages.YOU_HAVE_NO_ACCESS_TO_THIS_REVIEW);
        }
    }

    public void checkCanDeleteCustomerAllReviews(Customer customer) throws AccessDeniedException {
        UUID currentCustomerId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerSelfRequest = currentCustomerId.equals(customer.getUserId());

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

}
