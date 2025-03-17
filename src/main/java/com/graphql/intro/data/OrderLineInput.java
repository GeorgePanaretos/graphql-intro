package com.graphql.intro.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderLineInput {

    @NotNull(message = "Product ID is required.")
    private String productId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    private int quantity;

    public OrderLineInput() {}

    public OrderLineInput(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}