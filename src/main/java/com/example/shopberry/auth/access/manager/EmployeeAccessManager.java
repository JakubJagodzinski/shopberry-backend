package com.example.shopberry.auth.access.manager;

import com.example.shopberry.auth.access.SecurityUtils;
import com.example.shopberry.common.constants.messages.UserMessages;
import com.example.shopberry.domain.employees.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeAccessManager {

    private final SecurityUtils securityUtils;

    public void checkCanReadEmployee(Employee employee) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeSelfRequest = employee.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isEmployeeSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanUpdateEmployee(Employee employee) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeSelfRequest = employee.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isEmployeeSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

    public void checkCanDeleteEmployee(Employee employee) throws AccessDeniedException {
        UUID currentUserId = securityUtils.getCurrentUserId();

        boolean isAdminRequest = securityUtils.isAdmin();
        boolean isEmployeeSelfRequest = employee.getUserId().equals(currentUserId);

        if (!isAdminRequest && !isEmployeeSelfRequest) {
            throw new AccessDeniedException(UserMessages.YOU_HAVE_NO_ACCESS_TO_THIS_ACCOUNT);
        }
    }

}
