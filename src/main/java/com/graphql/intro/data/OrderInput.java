package com.graphql.intro.data;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderInput {

    @NotNull(message = "Customer ID is required.")
    private Long customerId;

    @NotNull(message = "Salesperson ID is required.")
    private Long salespersonId;

    @NotNull(message = "Order lines cannot be empty.")
    private List<OrderLineInput> orderLines;

    public OrderInput() {}

    public OrderInput(Long customerId, Long salespersonId, List<OrderLineInput> orderLines) {
        this.customerId = customerId;
        this.salespersonId = salespersonId;
        this.orderLines = orderLines;
    }
}