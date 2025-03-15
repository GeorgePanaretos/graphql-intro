package com.graphql.intro.controller;

import com.graphql.intro.data.Product;
import com.graphql.intro.repo.ProductRepository;
import com.graphql.intro.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final SubscriptionService subscriptionService;


    @Autowired
    public ProductController(ProductRepository productRepository, SubscriptionService subscriptionService) {
        this.productRepository = productRepository;
        this.subscriptionService = subscriptionService;
    }

    @QueryMapping
    public Iterable<Product> products(){
        return this.productRepository.findAll();
    }

    @MutationMapping
    public Product addProduct(@Argument Product product) {
        Product savedProduct = this.productRepository.save(product);
        subscriptionService.publishProduct(savedProduct); // ✅ Publish event for subscription
        return savedProduct;
    }
}