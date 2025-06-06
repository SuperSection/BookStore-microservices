package com.supersection.bookstore.orders.domain;

import com.supersection.bookstore.orders.clients.catalog.Product;
import com.supersection.bookstore.orders.clients.catalog.ProductServiceClient;
import com.supersection.bookstore.orders.domain.dtos.CreateOrderRequest;
import com.supersection.bookstore.orders.domain.dtos.OrderItem;
import com.supersection.bookstore.orders.domain.exception.InvalidOrderException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductServiceClient client;

    OrderValidator(ProductServiceClient client) {
        this.client = client;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = client.getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid Product code:" + item.code()));
            if (item.price().compareTo(product.price()) != 0) {
                log.error(
                        "Product price not matching. Actual price:{}, received price:{}",
                        product.price(),
                        item.price());
                throw new InvalidOrderException("Product price not matching");
            }
        }
    }
}
