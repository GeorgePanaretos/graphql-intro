package com.graphql.intro.data;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "ORDERS")
public class Order {

    @Id
    @Column(name = "ORDER_ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID", nullable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "SALESPERSON_ID", nullable = false, updatable = false)
    private Salesperson salesperson;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

}