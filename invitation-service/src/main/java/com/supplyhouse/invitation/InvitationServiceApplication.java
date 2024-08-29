package com.supplyhouse.invitation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.supplyhouse.invitation.client")
public class InvitationServiceApplication {
    private static Logger LOG = LoggerFactory.getLogger(InvitationServiceApplication.class);

    public static void main(String[] args) {
        LOG.info("SpringApplication Invitation Service before starts...");
        SpringApplication.run(InvitationServiceApplication.class, args);
        LOG.info("SpringApplication Invitation Service after starts...");
    }
}
