package com.example.shopberry.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    GUEST_READ("guest:read"),
    GUEST_UPDATE("guest:update"),
    GUEST_CREATE("guest:create"),
    GUEST_DELETE("guest:delete"),

    CUSTOMER_READ("customer:read"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_CREATE("customer:create"),
    CUSTOMER_DELETE("customer:delete"),

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_UPDATE("employee:update"),
    EMPLOYEE_CREATE("employee:create"),
    EMPLOYEE_DELETE("employee:delete"),

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete");

    private final String permission;

}
