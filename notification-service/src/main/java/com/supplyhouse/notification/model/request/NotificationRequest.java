package com.supplyhouse.notification.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private String senderId;
    private String recipientId;
    private String senderEmail;
    private String recipientEmail;
    private String token;
    private String notificationType;
}
