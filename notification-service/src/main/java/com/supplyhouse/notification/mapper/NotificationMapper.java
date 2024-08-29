package com.supplyhouse.notification.mapper;

import com.supplyhouse.notification.model.dto.NotificationDTO;
import com.supplyhouse.notification.model.entity.Notification;

public class NotificationMapper {

    public static NotificationDTO toNotificationDTO(Notification notificationEntity) {
        if (notificationEntity == null) {
            return null;
        }

        return NotificationDTO.builder()
                .notificationId(String.valueOf(notificationEntity.getId()))
                                .senderId(String.valueOf(notificationEntity.getSenderId()))
                                        .recipientId(String.valueOf(notificationEntity.getRecipientId()))
                                                .senderEmail(notificationEntity.getSenderEmail())
                                                        .recipientEmail(notificationEntity.getRecipientEmail())
                                                                .type(notificationEntity.getType().name())
                .emailContent(notificationEntity.getEmailContent())
                .build();
    }

    public static Notification toNotification(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        return Notification.builder()
                .senderId(Long.parseLong(dto.getSenderId()))
                .recipientId(Long.parseLong(dto.getRecipientId()))
                .senderEmail(dto.getSenderEmail())
                .recipientEmail(dto.getRecipientEmail())
                .build();

    }
}
