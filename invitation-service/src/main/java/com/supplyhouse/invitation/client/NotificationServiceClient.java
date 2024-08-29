package com.supplyhouse.invitation.client;

import com.supplyhouse.invitation.model.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${services.notification-service.url}")
public interface NotificationServiceClient {

    @PostMapping("/notification/api/send-mail")
    String notifyRecipient(@RequestBody NotificationDTO dto);
}
