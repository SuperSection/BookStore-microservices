package com.supersection.bookstore.orders.domain.dtos;

import com.supersection.bookstore.orders.domain.models.Address;
import com.supersection.bookstore.orders.domain.models.Customer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public record CreateOrderRequest(
        @Valid @NotEmpty(message = "Items cannot be empty") Set<OrderItem> items,
        @Valid Customer customer,
        @Valid Address deliveryAddress) {}
