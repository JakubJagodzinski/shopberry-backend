package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.complaints.Complaint;
import com.example.shopberry.domain.customers.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ComplaintAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isComplaintOwner(Complaint complaint) {
        UUID currentUserId = securityUtils.getCurrentUserId();

        return complaint.getOrder().getCustomer().getUserId().equals(currentUserId);
    }

    public void checkCanReadComplaint(Complaint complaint) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = isComplaintOwner(complaint);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

    public void checkCanReadCustomerAllComplaints(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isCustomerSelfRequest = currentUserId.equals(customer.getUserId());

        if (!isAdminRequest && !isEmployeeRequest && !isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);

        }
    }

    public void checkCanCreateComplaint(Customer customer) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isCustomerSelfRequest = currentUserId.equals(customer.getUserId());

        if (!isCustomerSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);

        }
    }

    public void checkCanUpdateComplaint(Complaint complaint) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = isComplaintOwner(complaint);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

    public void checkCanDeleteComplaint(Complaint complaint) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = isComplaintOwner(complaint);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

}
