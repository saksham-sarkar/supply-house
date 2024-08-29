package com.supplyhouse.invitation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {
    private String notificationId;
    private String senderId;
    private String recipientId;
    private String senderEmail;
    private String recipientEmail;
    private String type;
    private String emailContent;
    private String invitationToken;
}
