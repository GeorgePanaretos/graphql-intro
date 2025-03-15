package com.graphql.intro.controller;

import com.graphql.intro.data.Customer;
import com.graphql.intro.data.Order;
import com.graphql.intro.data.Product;
import com.graphql.intro.service.SubscriptionService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @SubscriptionMapping
    public Publisher<Customer> customerAdded() {
        return subscriptionService.getCustomerStream();
    }

    @SubscriptionMapping
    public Publisher<Order> orderAdded() {
        return subscriptionService.getOrderStream();
    }

    @SubscriptionMapping
    public Publisher<Product> productAdded() {
        return subscriptionService.getProductStream();
    }
}