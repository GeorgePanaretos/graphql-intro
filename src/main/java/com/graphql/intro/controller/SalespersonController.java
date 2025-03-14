package com.graphql.intro.controller;

import com.graphql.intro.data.Salesperson;
import com.graphql.intro.repo.SalespersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SalespersonController {

    private final SalespersonRepository salespersonRepository;

    @Autowired
    public SalespersonController(SalespersonRepository salespersonRepository) {
        this.salespersonRepository = salespersonRepository;
    }

    @QueryMapping
    public Iterable<Salesperson> salespeople(){
        return this.salespersonRepository.findAll();
    }

    @QueryMapping
    public Salesperson salespersonById(@Argument Long id){
        return this.salespersonRepository.findById(id).orElseThrow();
    }

    @QueryMapping
    public Salesperson salespersonByEmail(@Argument String email){
        return this.salespersonRepository.findSalespersonByEmail(email);
    }
}
