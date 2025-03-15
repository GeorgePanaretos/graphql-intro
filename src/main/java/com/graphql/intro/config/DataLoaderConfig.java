package com.graphql.intro.config;

import com.graphql.intro.data.Customer;
import com.graphql.intro.repo.CustomerRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
public class DataLoaderConfig {

    @Bean
    public DataLoaderRegistry dataLoaderRegistry(CustomerRepository customerRepository) {
        DataLoaderRegistry registry = new DataLoaderRegistry();
        registry.register("customerDataLoader", customerDataLoader(customerRepository));
        return registry;
    }

    private DataLoader<Long, Customer> customerDataLoader(CustomerRepository customerRepository) {
        BatchLoader<Long, Customer> batchLoader = customerIds -> CompletableFuture.supplyAsync(() -> {
            List<Customer> customers = customerRepository.findAllById(customerIds);
            Map<Long, Customer> customerMap = customers.stream()
                    .collect(Collectors.toMap(Customer::getId, customer -> customer));
            return customerIds.stream().map(customerMap::get).collect(Collectors.toList());
        });

        return DataLoader.newDataLoader(batchLoader);
    }
}