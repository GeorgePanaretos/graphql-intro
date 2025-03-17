package com.graphql.intro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.graphql.intro.data.Order;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAll();
}