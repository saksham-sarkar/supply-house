package com.supplyhouse.invitation.model.entity;

import com.supplyhouse.invitation.model.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INVITATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_owner_id", nullable = false)
    private Long businessOwnerId;  // Business Owner ID who sent the invitation

    @Column(name = "sub_account_id", nullable = false)
    private Long subAccountId; // SubAccount ID receiving the invitation

    @Column(name = "invitation_token", unique = true, nullable = false)
    private String invitationToken; // Unique token for the invitation

    @Column(name = "sender_email", nullable = false)
    private String senderEmail; // Dynamic sender email (from business owner)

    @Column(name = "recipient_email", nullable = false)
    private String recipientEmail; // SubAccount email (recipient)

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt; // Timestamp when the invitation was sent

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus status; // Status of the invitation (PENDING, ACCEPTED, DECLINED)

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt; // Timestamp when the invitation was accepted

    @Column(name = "declined_at")
    private LocalDateTime declinedAt; // Timestamp when the invitation was declined
}
