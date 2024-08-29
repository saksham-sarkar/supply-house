package com.supplyhouse.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationServiceApplication {
    private static Logger LOG = LoggerFactory.getLogger(NotificationServiceApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringApplication Notification Service before starts...");
        SpringApplication.run(NotificationServiceApplication.class, args);
        LOG.info("SpringApplication Notification Service after starts...");
    }
}
