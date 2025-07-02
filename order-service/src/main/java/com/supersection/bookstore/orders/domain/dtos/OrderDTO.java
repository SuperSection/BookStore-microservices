package com.supersection.bookstore.orders.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.supersection.bookstore.orders.domain.models.Address;
import com.supersection.bookstore.orders.domain.models.Customer;
import com.supersection.bookstore.orders.domain.models.OrderItem;
import com.supersection.bookstore.orders.domain.models.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
        String orderNumber,
        String user,
        Set<OrderItem> items,
        Customer customer,
        Address deliveryAddress,
        OrderStatus status,
        String comments,
        LocalDateTime createdAt) {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
