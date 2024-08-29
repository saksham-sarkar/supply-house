package com.supplyhouse.orchestration.client;

import com.supplyhouse.orchestration.model.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${services.order-service.url}")
public interface OrderServiceClient {

    @GetMapping("/orders/api/has-placed-ten-orders/{accountId}")
    boolean hasPlacedTenOrders(@PathVariable String accountId);

    @GetMapping("/orders/api/fetch-all/{accountId}")
    List<OrderDTO> fetchAll(@PathVariable String accountId);

    @GetMapping("/orders/api/fetch")
    List<OrderDTO> fetchByDate(@RequestParam String accountId,
                            @RequestParam String orderDate);

    @PostMapping("/orders/api/create")
    Long save(@RequestBody OrderDTO orderDTO);
}
