package com.supplyhouse.order.controller;

import com.supplyhouse.order.model.dto.OrderDTO;
import com.supplyhouse.order.model.entity.Order;
import com.supplyhouse.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.supplyhouse.order.mapper.OrderMapper.toOrder;
import static com.supplyhouse.order.mapper.OrderMapper.toOrderDTO;

@RestController
@RequestMapping("/orders/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/has-placed-ten-orders/{accountId}")
    public boolean hasPlacedTenOrders(@PathVariable String accountId) {
        return orderService.hasPlacedTenOrders(Long.parseLong(accountId));
    }

    @GetMapping("/fetch-all/{accountId}")
    List<OrderDTO> fetchAll(
            @PathVariable String accountId) {
        List<Order> orders = orderService.fetchAll(Long.parseLong(accountId));
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order orderEntity : orders) {
            orderDTOS.add(toOrderDTO(orderEntity));
        }
        return orderDTOS;
    }

    @GetMapping("/fetch")
    List<OrderDTO> fetchByDate(@RequestParam String accountId,
                      @RequestParam String orderDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime orderLocalDate = LocalDateTime.parse(orderDate, formatter);
        List<Order> orders = orderService.fetchByDate(Long.parseLong(accountId), orderLocalDate);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order orderEntity : orders) {
            orderDTOS.add(toOrderDTO(orderEntity));
        }
        return orderDTOS;
    }

    @GetMapping("/{orderId}")
    OrderDTO getAccount(@PathVariable String orderId) {
        Order order = orderService.getOrder(Long.parseLong(orderId));
        return toOrderDTO(order);
    }

    @PostMapping("/create")
    Long save(@RequestBody OrderDTO dto) {
        Order order = toOrder(dto);
        return orderService.save(order);
    }

}
