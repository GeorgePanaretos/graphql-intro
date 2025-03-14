package com.graphql.intro.controller;

import com.graphql.intro.data.Product;
import com.graphql.intro.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryMapping
    public Iterable<Product> products(){
        return this.productRepository.findAll();
    }

    @QueryMapping
    public Product productById(@Argument String id){
        return this.productRepository.findById(id).orElseThrow();
    }
}