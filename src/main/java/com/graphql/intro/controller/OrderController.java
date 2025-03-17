package com.graphql.intro.controller;

import com.graphql.intro.data.*;
import com.graphql.intro.repo.CustomerRepository;
import com.graphql.intro.repo.OrderRepository;
import com.graphql.intro.repo.ProductRepository;
import com.graphql.intro.repo.SalespersonRepository;
import com.graphql.intro.response.GraphQLResponse;
import com.graphql.intro.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import java.util.UUID;


@Controller
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SalespersonRepository salespersonRepository;
    private final ProductRepository productRepository;
    private final SubscriptionService subscriptionService;

    public OrderController(OrderRepository orderRepository,
                           CustomerRepository customerRepository,
                           SalespersonRepository salespersonRepository,
                           ProductRepository productRepository,
                           SubscriptionService subscriptionService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.salespersonRepository = salespersonRepository;
        this.productRepository = productRepository;
        this.subscriptionService = subscriptionService;
    }


    @QueryMapping
    public List<Order> orders() {
        return orderRepository.findAll();
    }

    @QueryMapping
    public Order orderById(@Argument String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found."));
    }

    @MutationMapping
    public GraphQLResponse<Order> addOrder(@Valid @Argument(name = "input") OrderInput orderInput) {
        Customer customer = customerRepository.findById(orderInput.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Salesperson salesperson = salespersonRepository.findById(orderInput.getSalespersonId())
                .orElseThrow(() -> new RuntimeException("Salesperson not found"));

        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setSalesperson(salesperson);

        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderLineInput lineInput : orderInput.getOrderLines()) {
            Product product = productRepository.findById(lineInput.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            orderLines.add(new OrderLine(order, product, lineInput.getQuantity()));
        }

        order.setOrderLines(orderLines);
        Order savedOrder = orderRepository.save(order);
        subscriptionService.publishOrder(savedOrder);

        return GraphQLResponse.success(savedOrder);
    }
}