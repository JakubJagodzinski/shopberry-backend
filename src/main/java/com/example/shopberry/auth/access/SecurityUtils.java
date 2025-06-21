package com.example.shopberry.auth.access;

import com.example.shopberry.user.Role;
import com.example.shopberry.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SecurityUtils {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UUID getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public boolean isAdmin() {
        return hasRole(Role.ADMIN.name());
    }

    public boolean isEmployee() {
        return hasRole(Role.EMPLOYEE.name());
    }

    public boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public boolean hasPermission(String authority) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

}
