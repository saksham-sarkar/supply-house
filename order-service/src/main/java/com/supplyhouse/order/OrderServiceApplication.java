package com.supplyhouse.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication {
    private static Logger LOG = LoggerFactory.getLogger(OrderServiceApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringApplication Order Service before starts...");
        SpringApplication.run(OrderServiceApplication.class, args);
        LOG.info("SpringApplication Order Service after starts...");
    }
}
