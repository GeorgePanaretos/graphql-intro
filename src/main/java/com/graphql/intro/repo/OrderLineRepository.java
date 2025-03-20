package com.graphql.intro.repo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.graphql.intro.data.OrderLine;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    // 🔍 Find all order lines linked to a specific product
    List<OrderLine> findByProductId(String productId);

    // 🔥 Delete all order lines referencing a specific product (Transactional to ensure integrity)
    @Transactional
    void deleteByProductId(String productId);
}
