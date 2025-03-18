package com.graphql.intro.data;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderInput {

    @NotNull(message = "Customer ID is required.")
    private Long customerId;

    @NotNull(message = "Salesperson ID is required.")
    private Long salespersonId;

    @NotNull(message = "Order lines cannot be empty.")
    private List<OrderLineInput> orderLines;

}