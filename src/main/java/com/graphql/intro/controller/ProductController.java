package com.graphql.intro.controller;

import com.graphql.intro.data.Product;
import com.graphql.intro.data.ProductInput;
import com.graphql.intro.repo.ProductRepository;
import com.graphql.intro.response.GraphQLResponse;
import com.graphql.intro.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Iterable<Product> products() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id) {
       return  this.productRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Product not found."));
    }

    @MutationMapping
    public GraphQLResponse<Product> addProduct(@Valid @Argument(name = "input") ProductInput productInput) {
//        // Retrieve all products with the same name
//        List<Product> existingProducts = productRepository.findByName(productInput.getName());
//
//        // If there are any existing products, return an error
//        if (!existingProducts.isEmpty()) {
//            return GraphQLResponse.error("Product with this name already exists.");
//        }

        // Create a new Product entity
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(productInput.getName());
        product.setSize(productInput.getSize());
        product.setVariety(productInput.getVariety());
        product.setPrice(productInput.getPrice());
        product.setStatus(productInput.getStatus());

        // Save to repository
        Product savedProduct = productRepository.save(product);

        // Publish event for subscription
        subscriptionService.publishProduct(savedProduct);

        return GraphQLResponse.success(savedProduct);
    }
}