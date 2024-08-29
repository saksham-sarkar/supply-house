package com.supplyhouse.invitation.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvitationDTO {
    private String invitationId;
    private String businessOwnerId;
    private String subAccountId;
    private String invitationToken;
    private String senderEmail;
    private String recipientEmail;
    private String sentAt;
    private String status;
    private String acceptedAt;
    private String declinedAt;
}
