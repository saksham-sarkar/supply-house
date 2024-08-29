package com.supplyhouse.order.service;

import com.supplyhouse.order.exception.OrderServiceException;
import com.supplyhouse.order.model.entity.Order;
import com.supplyhouse.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public boolean hasPlacedTenOrders(Long accountId) {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        // Fetch all orders after this date and check if there are at least 10
        List<Order> recentOrders = orderRepository.findByAccountIdAndOrderDateAfter(accountId, oneYearAgo);
        return recentOrders.size() >= 10;
    }

    public List<Order> fetchAll(Long accountId) {
        return orderRepository.findAllByAccountId(accountId);
    }

    public List<Order> fetchByDate(Long accountId, LocalDateTime orderDate) {

        return orderRepository.findByAccountIdAndOrderDateAfter(accountId, orderDate);
    }

    public Long save(Order order) {
        Order orderSaved = orderRepository.save(order);
        return orderSaved.getOrderId();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderServiceException("Order not found for orderId = " + orderId));
    }
}
