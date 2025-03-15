package com.graphql.intro.controller;

import com.graphql.intro.data.Order;
import com.graphql.intro.repo.OrderRepository;
import com.graphql.intro.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public OrderController(OrderRepository orderRepository, SubscriptionService subscriptionService) {
        this.orderRepository = orderRepository;
        this.subscriptionService = subscriptionService;
    }

    @QueryMapping
    public Iterable<Order> orders(){
        return this.orderRepository.findAll();
    }

    @MutationMapping
    public Order addOrder(@Argument Order order) {
        Order savedOrder = this.orderRepository.save(order);
        subscriptionService.publishOrder(savedOrder); // ✅ Publish event for subscription
        return savedOrder;
    }
}