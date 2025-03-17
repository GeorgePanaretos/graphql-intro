package com.graphql.intro.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInput {
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    private int size;

    @NotBlank(message = "Variety cannot be empty")
    private String variety;

    @Positive(message = "Price must be a positive value")
    private BigDecimal price;

    @NotBlank(message = "Status cannot be empty")
    private String status;
}