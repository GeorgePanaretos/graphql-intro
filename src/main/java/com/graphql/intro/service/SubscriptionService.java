package com.graphql.intro.service;

import com.graphql.intro.data.Customer;
import com.graphql.intro.data.Order;
import com.graphql.intro.data.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class SubscriptionService {
    private final List<Consumer<Customer>> customerSubscribers = new ArrayList<>();
    private final List<Consumer<Order>> orderSubscribers = new ArrayList<>();
    private final List<Consumer<Product>> productSubscribers = new ArrayList<>();

    // ✅ CUSTOMER SUBSCRIPTION
    public Flux<Customer> getCustomerStream() {
        return Flux.create(sink -> customerSubscribers.add(sink::next), FluxSink.OverflowStrategy.LATEST);
    }

    public void publishCustomer(Customer customer) {
        customerSubscribers.forEach(subscriber -> subscriber.accept(customer));
    }

    // ✅ ORDER SUBSCRIPTION
    public Flux<Order> getOrderStream() {
        return Flux.create(sink -> orderSubscribers.add(sink::next), FluxSink.OverflowStrategy.LATEST);
    }

    public void publishOrder(Order order) {
        orderSubscribers.forEach(subscriber -> subscriber.accept(order));
    }

    // ✅ PRODUCT SUBSCRIPTION
    public Flux<Product> getProductStream() {
        return Flux.create(sink -> productSubscribers.add(sink::next), FluxSink.OverflowStrategy.LATEST);
    }

    public void publishProduct(Product product) {
        productSubscribers.forEach(subscriber -> subscriber.accept(product));
    }
}