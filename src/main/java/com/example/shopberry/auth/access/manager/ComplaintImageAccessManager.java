package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.ComplaintMessages;
import com.example.shopberry.domain.complaintimages.ComplaintImage;
import com.example.shopberry.domain.complaints.Complaint;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ComplaintImageAccessManager {

    private final SecurityUtils securityUtils;

    private boolean isComplaintOwner(ComplaintImage complaintImage) {
        UUID currentUserId = securityUtils.getCurrentUserId();

        return complaintImage.getComplaint().getOrder().getCustomer().getUserId().equals(currentUserId);
    }

    public void checkCanReadComplaintImage(ComplaintImage complaintImage) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = isComplaintOwner(complaintImage);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

    public void checkCanReadComplaintAllImages(Complaint complaint) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = complaint.getOrder().getCustomer().getUserId().equals(currentUserId);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

    public void checkCanAddImageToComplaint(Complaint complaint) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isComplaintOwnerRequest = currentUserId.equals(complaint.getOrder().getCustomer().getUserId());

        if (!isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

    public void checkCanDeleteComplaintImage(ComplaintImage complaintImage) throws AccessDeniedException {
        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeRequest = securityUtils.isEmployee();
        boolean isComplaintOwnerRequest = isComplaintOwner(complaintImage);

        if (!isAdminRequest && !isEmployeeRequest && !isComplaintOwnerRequest) {
            throw new AccessDeniedException(ComplaintMessages.YOU_HAVE_NO_ACCESS_TO_THIS_COMPLAINT);
        }
    }

}
