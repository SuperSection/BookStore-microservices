package com.supersection.bookstore.notifications.event;

import com.supersection.bookstore.notifications.domain.models.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class OrderEventHandler {

    @RabbitListener(queues = "${notifications.new-notification-queue}")
    void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Order Created Event: " + event);
    }
}
