package com.graphql.intro.controller;


import com.graphql.intro.data.Customer;
import com.graphql.intro.data.CustomerInput;
import com.graphql.intro.repo.CustomerRepository;
import com.graphql.intro.response.GraphQLResponse;
import com.graphql.intro.service.SubscriptionService;
import jakarta.validation.Valid;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final SubscriptionService subscriptionService;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, SubscriptionService subscriptionService) {
        this.customerRepository = customerRepository;
        this.subscriptionService = subscriptionService;
    }

    @QueryMapping
    public Iterable<Customer> customers() {
        return this.customerRepository.findAll();
    }

    @QueryMapping
    public CompletableFuture<Customer> customerById(@Argument Long id, DataLoader<Long, Customer> customerDataLoader) {
        return customerDataLoader.load(id);
    }

//    @QueryMapping
//    public Customer customerById(@Argument Long id) {
//        return this.customerRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Customer not found."));
//    }

    @QueryMapping
    public Customer customerByEmail(@Argument String email) {
        return this.customerRepository.findCustomerByEmail(email);
    }

    @MutationMapping
    public GraphQLResponse<Customer> addCustomer(@Valid @Argument(name = "input") CustomerInput customerInput) {
        Customer existingCustomer = customerRepository.findCustomerByEmail(customerInput.getEmail());
        if (existingCustomer != null) {
            return GraphQLResponse.error("Customer with this email already exists.");
        }

        Customer customer = this.customerRepository.save(customerInput.getCustomerEntity());
        subscriptionService.publishCustomer(customer);
        return GraphQLResponse.success(customer);
    }
}