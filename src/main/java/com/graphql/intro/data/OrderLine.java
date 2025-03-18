package com.graphql.intro.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ORDER_LINES")
public class OrderLine {

    @Id
    @Column(name = "ORDER_LINE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @JoinColumn(name = "ORDER_ID", nullable = false, updatable = false)
    @ManyToOne
    private Order order;
    @OneToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false)
    private Product product;
    @Column(name = "QUANTITY")
    private int quantity;

    public OrderLine(Order order, Product product, int quantity) {
    }
}
