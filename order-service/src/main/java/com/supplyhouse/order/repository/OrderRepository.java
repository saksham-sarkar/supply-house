package com.supplyhouse.order.repository;

import com.supplyhouse.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByAccountIdAndOrderDateAfter(Long account, LocalDateTime date);
    // Find all orders for a specific account
    List<Order> findAllByAccountId(Long accountId);
}

