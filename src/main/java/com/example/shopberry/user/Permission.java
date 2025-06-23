package com.example.shopberry.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    ATTRIBUTE_CREATE("attribute:create"),
    ATTRIBUTE_UPDATE("attribute:update"),
    ATTRIBUTE_DELETE("attribute:delete"),

    CART_PRODUCT_ADD("cart_product:add"),
    CART_PRODUCT_UPDATE("cart_product:update"),
    CART_PRODUCT_REMOVE("cart_product:remove"),

    CUSTOMER_CART_PRODUCT_READ("customer:cart_product:read"),
    CUSTOMER_CART_PRODUCT_READ_ALL("customer:cart_product:read:all"),

    CATEGORY_CREATE("category:create"),
    CATEGORY_UPDATE("category:update"),
    CATEGORY_DELETE("category:delete"),

    CATEGORY_ATTRIBUTE_ASSIGN("category:attribute:assign"),
    CATEGORY_ATTRIBUTE_UNASSIGN("category:attribute:unassign"),

    CAUSE_OF_RETURN_CREATE("cause_of_return:create"),
    CAUSE_OF_RETURN_UPDATE("cause_of_return:update"),
    CAUSE_OF_RETURN_DELETE("cause_of_return:delete"),

    COMPLAINT_IMAGE_ADD("complaint_image:add"),
    COMPLAINT_IMAGE_READ("complaint_image:read"),
    COMPLAINT_IMAGE_READ_ALL("complaint_image:read:all"),
    COMPLAINT_IMAGE_DELETE("complaint_image:delete"),

    COMPLAINT_COMPLAINT_IMAGE_READ_ALL("complaint:complaint_image:read:all"),

    COMPLAINT_CREATE("complaint:create"),
    COMPLAINT_READ("complaint:read"),
    COMPLAINT_READ_ALL("complaint:read:all"),
    COMPLAINT_UPDATE("complaint:update"),
    COMPLAINT_DELETE("complaint:delete"),

    ADDRESS_CREATE("address:create"),
    ADDRESS_READ("address:read"),
    ADDRESS_READ_ALL("address:read:all"),
    ADDRESS_UPDATE("address:update"),
    ADDRESS_DELETE("address:delete"),

    CUSTOMER_ADDRESS_READ_ALL("customer_address:read:all"),
    CUSTOMER_ADDRESS_DELETE_ALL("customer_address:delete_all"),

    CUSTOMER_READ("customer:read"),
    CUSTOMER_READ_ALL("customer:read:all"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete"),

    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_READ_ALL("employee:read:all"),
    EMPLOYEE_UPDATE("employee:update"),
    EMPLOYEE_DELETE("employee:delete"),

    CUSTOMER_FAVOURITE_PRODUCT_ADD("customer_favourite_product:add"),
    CUSTOMER_FAVOURITE_PRODUCT_READ_ALL("customer_favourite_product:read:all"),
    CUSTOMER_FAVOURITE_PRODUCT_REMOVE("customer_favourite_product:remove"),

    ORDER_PRODUCT_READ("order:product:read"),
    ORDER_PRODUCT_READ_ALL("order:product:read:all"),

    ORDER_PRODUCT_STATUS_CREATE("order_product_status:create"),
    ORDER_PRODUCT_STATUS_READ("order_product_status:read"),
    ORDER_PRODUCT_STATUS_READ_ALL("order_product_status:read:all"),
    ORDER_PRODUCT_STATUS_UPDATE("order_product_status:update"),
    ORDER_PRODUCT_STATUS_DELETE("order_product_status:delete"),

    ORDER_CREATE("order:create"),
    ORDER_READ("order:read"),
    ORDER_READ_ALL("order:read:all"),
    ORDER_DELETE("order:delete"),

    CUSTOMER_ORDER_READ_ALL("customer:order:read:all"),

    ORDER_STATUS_CREATE("order_status:create"),
    ORDER_STATUS_READ("order_status:read"),
    ORDER_STATUS_READ_ALL("order_status:read:all"),
    ORDER_STATUS_UPDATE("order_status:update"),
    ORDER_STATUS_DELETE("order_status:delete"),

    ORDER_PRODUCT_RETURN_READ_ALL("order:product_return:read:all"),

    PAYMENT_TYPE_CREATE("payment_type:create"),
    PAYMENT_TYPE_UPDATE("payment_type:update"),
    PAYMENT_TYPE_DELETE("payment_type:delete"),

    PRODUCER_CREATE("producer:create"),
    PRODUCER_READ_ALL("producer:read:all"),
    PRODUCER_UPDATE("producer:update"),
    PRODUCER_DELETE("producer:delete"),

    PRODUCT_ATTRIBUTE_ASSIGN("product:attribute:assign"),
    PRODUCT_ATTRIBUTE_UPDATE("product:attribute:update"),
    PRODUCT_ATTRIBUTE_UNASSIGN("product:attribute:unassign"),

    PRODUCT_PROMOTION_ASSIGN("product:promotion:assign"),
    PRODUCT_PROMOTION_UNASSIGN("product:promotion:unassign"),
    PRODUCT_PROMOTION_UNASSIGN_ALL("product:promotion:unassign:all"),

    PRODUCT_RETURN_CREATE("product_return:create"),
    PRODUCT_RETURN_READ("product_return:read"),
    PRODUCT_RETURN_DELETE("product_return:delete"),

    PRODUCT_CREATE("product:create"),
    PRODUCT_UPDATE("product:update"),
    PRODUCT_DELETE("product:delete"),

    PROMOTION_CREATE("promotion:create"),
    PROMOTION_UPDATE("promotion:update"),
    PROMOTION_DELETE("promotion:delete"),

    REVIEW_CREATE("review:create"),
    REVIEW_UPDATE("review:update"),
    REVIEW_DELETE("review:delete"),

    CUSTOMER_REVIEW_DELETE_ALL("customer:review:delete:all"),
    PRODUCT_REVIEW_DELETE_ALL("product:review:delete:all"),

    SHIPMENT_TYPE_CREATE("shipment_type:create"),
    SHIPMENT_TYPE_UPDATE("shipment_type:update"),
    SHIPMENT_TYPE_DELETE("shipment_type:delete");

    private final String permission;

}
