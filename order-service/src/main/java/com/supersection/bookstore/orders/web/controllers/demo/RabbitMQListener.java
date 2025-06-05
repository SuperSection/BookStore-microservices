package com.supersection.bookstore.orders.web.controllers.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
class RabbitMQListener {

    @RabbitListener(queues = "${orders.new-orders-queue}")
    public void handleNewOrders(MyPayload payload) {
        System.out.println("New Order: " + payload.content());
    }

    @RabbitListener(queues = "${orders.delivered-orders-queue}")
    public void handleDeliveredOrder(MyPayload payload) {
        System.out.println("Delivered Order: " + payload.content());
    }
}
