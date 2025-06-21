package com.example.shopberry.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.shopberry.user.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {

    CUSTOMER(
            Set.of(
                    CART_PRODUCT_ADD,
                    CART_PRODUCT_UPDATE,
                    CART_PRODUCT_REMOVE,

                    CUSTOMER_CART_PRODUCT_READ,
                    CUSTOMER_CART_PRODUCT_READ_ALL,

                    COMPLAINT_IMAGE_ADD,
                    COMPLAINT_IMAGE_READ,
                    COMPLAINT_IMAGE_DELETE,

                    COMPLAINT_COMPLAINT_IMAGE_READ_ALL,

                    COMPLAINT_CREATE,
                    COMPLAINT_READ,
                    COMPLAINT_UPDATE,
                    COMPLAINT_DELETE,

                    ADDRESS_CREATE,
                    ADDRESS_READ,
                    ADDRESS_UPDATE,
                    ADDRESS_DELETE,

                    CUSTOMER_ADDRESS_READ_ALL,
                    CUSTOMER_ADDRESS_DELETE_ALL,

                    CUSTOMER_READ,
                    CUSTOMER_UPDATE,
                    CUSTOMER_DELETE,

                    CUSTOMER_FAVOURITE_PRODUCT_ADD,
                    CUSTOMER_FAVOURITE_PRODUCT_READ_ALL,
                    CUSTOMER_FAVOURITE_PRODUCT_REMOVE,

                    ORDER_PRODUCT_ADD,
                    ORDER_PRODUCT_READ,
                    ORDER_PRODUCT_REMOVE,

                    ORDER_PRODUCT_STATUS_READ,
                    ORDER_PRODUCT_STATUS_READ_ALL,

                    ORDER_CREATE,
                    ORDER_READ,

                    CUSTOMER_ORDER_READ_ALL,

                    ORDER_STATUS_READ,
                    ORDER_STATUS_READ_ALL,

                    PRODUCT_RETURN_CREATE,

                    REVIEW_CREATE,
                    REVIEW_UPDATE,
                    REVIEW_DELETE,

                    CUSTOMER_REVIEW_DELETE_ALL
            )
    ),

    EMPLOYEE(
            Set.of(
                    ATTRIBUTE_CREATE,
                    ATTRIBUTE_UPDATE,
                    ATTRIBUTE_DELETE,

                    CATEGORY_CREATE,
                    CATEGORY_UPDATE,
                    CATEGORY_DELETE,

                    CATEGORY_ATTRIBUTE_ASSIGN,
                    CATEGORY_ATTRIBUTE_UNASSIGN,

                    COMPLAINT_READ,
                    COMPLAINT_READ_ALL,

                    CUSTOMER_READ,
                    CUSTOMER_READ_ALL,

                    EMPLOYEE_READ,
                    EMPLOYEE_UPDATE,
                    EMPLOYEE_DELETE,

                    EMPLOYEE_TYPE_READ,
                    EMPLOYEE_TYPE_READ_ALL,

                    ORDER_PRODUCT_STATUS_READ,
                    ORDER_PRODUCT_STATUS_READ_ALL,

                    ORDER_STATUS_READ,
                    ORDER_STATUS_READ_ALL,

                    PRODUCT_ATTRIBUTE_ASSIGN,
                    PRODUCT_ATTRIBUTE_UNASSIGN,

                    PRODUCT_PROMOTION_ASSIGN,
                    PRODUCT_PROMOTION_UNASSIGN,
                    PRODUCT_PROMOTION_UNASSIGN_ALL,

                    PRODUCT_CREATE,
                    PRODUCT_UPDATE,
                    PRODUCT_DELETE,

                    PROMOTION_CREATE,
                    PROMOTION_UPDATE,
                    PROMOTION_DELETE
            )
    ),

    ADMIN(
            Set.of(
                    Permission.values()
            )
    );

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
