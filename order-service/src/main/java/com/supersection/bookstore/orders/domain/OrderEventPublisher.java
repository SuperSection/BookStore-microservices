package com.supersection.bookstore.orders.domain;

import com.supersection.bookstore.orders.ApplicationProperties;
import com.supersection.bookstore.orders.domain.models.OrderCancelledEvent;
import com.supersection.bookstore.orders.domain.models.OrderCreatedEvent;
import com.supersection.bookstore.orders.domain.models.OrderDeliveredEvent;
import com.supersection.bookstore.orders.domain.models.OrderErrorEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class OrderEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(OrderEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties properties;

    OrderEventPublisher(RabbitTemplate rabbitTemplate, ApplicationProperties properties) {
        this.rabbitTemplate = rabbitTemplate;
        this.properties = properties;
    }

    public void publish(OrderCreatedEvent event) {
        this.send(properties.newOrdersQueue(), event);
    }

    public void publish(OrderDeliveredEvent event) {
        this.send(properties.deliveredOrdersQueue(), event);
    }

    public void publish(OrderCancelledEvent event) {
        this.send(properties.cancelledOrdersQueue(), event);
    }

    public void publish(OrderErrorEvent event) {
        this.send(properties.errorOrdersQueue(), event);
    }

    private void send(String routingKey, Object payload) {
        try {
            log.info("Publishing event to exchange: {}, routingKey: {}", properties.orderEventsExchange(), routingKey);
            rabbitTemplate.convertAndSend(properties.orderEventsExchange(), routingKey, payload);
            log.info("Successfully published event to routingKey: {}", routingKey);
        } catch (Exception e) {
            log.error("Failed to publish event to routingKey: {}, error: {}", routingKey, e.getMessage(), e);
            throw e;
        }
    }
}
