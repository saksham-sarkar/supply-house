package com.supplyhouse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountServiceApplication {
    private static Logger LOG = LoggerFactory.getLogger(AccountServiceApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringApplication Account Service before starts...");
        SpringApplication.run(AccountServiceApplication.class, args);
        LOG.info("SpringApplication Account Service after starts...");
    }
}
