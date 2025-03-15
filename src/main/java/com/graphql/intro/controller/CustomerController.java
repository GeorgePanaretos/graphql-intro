package com.graphql.intro.controller;


import com.graphql.intro.data.Customer;
import com.graphql.intro.data.CustomerInput;
import com.graphql.intro.repo.CustomerRepository;
import com.graphql.intro.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

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
    public Customer customerById(@Argument Long id) {
        return this.customerRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Customer customerByEmail(@Argument String email) {
        return this.customerRepository.findCustomerByEmail(email);
    }

//    @MutationMapping
//    public Customer addCustomer(@Argument(name = "input") CustomerInput customerInput) {
//        Customer customer = this.customerRepository.save(customerInput.getCustomerEntity());
//        subscriptionService.publishCustomer(customer); // ✅ New approach
//        return customer;
//    }
@MutationMapping
public Customer addCustomer(@Argument(name = "input") CustomerInput customerInput) {
    // ✅ Check if customer with this email already exists
    Customer existingCustomer = customerRepository.findCustomerByEmail(customerInput.getEmail());

    if (existingCustomer != null) {
        System.out.println("⚠️ Customer with email already exists: " + existingCustomer.getEmail());
        return existingCustomer; // ✅ Return the existing customer instead of throwing an error
    }

    // ✅ Email is unique, so we can safely insert the new customer
    Customer customer = this.customerRepository.save(customerInput.getCustomerEntity());
    subscriptionService.publishCustomer(customer); // Notify subscriptions
    return customer;
}
}