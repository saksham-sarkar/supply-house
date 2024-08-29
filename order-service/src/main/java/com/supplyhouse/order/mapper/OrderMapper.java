package com.supplyhouse.order.mapper;



import com.supplyhouse.order.model.dto.OrderDTO;
import com.supplyhouse.order.model.entity.Order;

import java.time.LocalDateTime;

public class OrderMapper {

    public static OrderDTO toOrderDTO(Order orderEntity) {
        if (orderEntity == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setOrderId(String.valueOf(orderEntity.getOrderId()));
        dto.setAccountId(String.valueOf(orderEntity.getAccountId()));

        return dto;
    }

    public static Order toOrder(OrderDTO dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                        .accountId(Long.parseLong(dto.getAccountId()))
                        .orderDate(LocalDateTime.now())
                .build();

    }
}
