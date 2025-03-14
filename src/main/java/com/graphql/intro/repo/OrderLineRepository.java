package com.graphql.intro.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.graphql.intro.data.OrderLine;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
