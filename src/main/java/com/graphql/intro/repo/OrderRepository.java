package com.graphql.intro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graphql.intro.data.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}