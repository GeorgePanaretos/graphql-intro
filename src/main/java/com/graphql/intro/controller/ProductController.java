package com.graphql.intro.controller;

import com.graphql.intro.data.Order;
import com.graphql.intro.data.OrderLine;
import com.graphql.intro.data.Product;
import com.graphql.intro.data.ProductInput;
import com.graphql.intro.repo.OrderLineRepository;
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
    private final OrderLineRepository orderLineRepository;

    @Autowired
    public ProductController(ProductRepository productRepository, SubscriptionService subscriptionService, OrderLineRepository orderLineRepository) {
        this.productRepository = productRepository;
        this.subscriptionService = subscriptionService;
        this.orderLineRepository = orderLineRepository;
    }

    @QueryMapping
    public List<Product> products() {
        return productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id) {
        return this.productRepository.findById(id)
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

    @MutationMapping
    public GraphQLResponse<Product> updateProduct(@Argument String id, @Valid @Argument(name = "input") ProductInput productInput) {
        // Check if the product exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update only provided fields
        if (productInput.getName() != null) {
            existingProduct.setName(productInput.getName());
        }
        if (productInput.getSize() != 0) {
            existingProduct.setSize(productInput.getSize());
        }
        if (productInput.getVariety() != null) {
            existingProduct.setVariety(productInput.getVariety());
        }
        if (productInput.getPrice() != null) {
            existingProduct.setPrice(productInput.getPrice());
        }
        if (productInput.getStatus() != null) {
            existingProduct.setStatus(productInput.getStatus());
        }

        // Save updated product
        Product updatedProduct = productRepository.save(existingProduct);

        // Publish update event for subscriptions
        subscriptionService.publishProduct(updatedProduct);

        return GraphQLResponse.success(updatedProduct);
    }

    @MutationMapping
    public GraphQLResponse<Boolean> deleteProduct(@Argument String id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return GraphQLResponse.error("Product not found.");
        }

        // 🔥 Step 1: Fetch all order lines associated with this product
        List<OrderLine> orderLines = orderLineRepository.findByProductId(id);

        if (!orderLines.isEmpty()) {
            // 🔥 Step 2: Remove order lines before deleting the product
            orderLineRepository.deleteByProductId(id);
        }

        // 🔥 Step 3: Delete the product
        productRepository.deleteById(id);

        return GraphQLResponse.success(true);
    }
}