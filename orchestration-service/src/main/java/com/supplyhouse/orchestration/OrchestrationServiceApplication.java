package com.supplyhouse.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.supplyhouse.orchestration.client")
public class OrchestrationServiceApplication {
    private static Logger LOG = LoggerFactory.getLogger(OrchestrationServiceApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringApplication Orchestration Service before starts...");
        SpringApplication.run(OrchestrationServiceApplication.class, args);
        LOG.info("SpringApplication Orchestration Service after starts...");
    }
}
