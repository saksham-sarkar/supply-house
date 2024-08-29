package com.supplyhouse.notification.controller;

import com.supplyhouse.notification.model.dto.NotificationDTO;
import com.supplyhouse.notification.model.entity.Notification;
import com.supplyhouse.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.supplyhouse.notification.mapper.NotificationMapper.toNotification;

@RestController
@RequestMapping("/notification/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-mail")
    public String notifyRecipient(@RequestBody NotificationDTO dto) {
        Notification notification = toNotification(dto);
        return notificationService.notifyRecipient(notification, dto.getInvitationToken());
    }
}
