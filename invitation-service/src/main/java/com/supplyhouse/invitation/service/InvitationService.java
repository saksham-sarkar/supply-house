package com.supplyhouse.invitation.service;

import com.supplyhouse.invitation.client.NotificationServiceClient;
import com.supplyhouse.invitation.exception.InvitationServiceException;
import com.supplyhouse.invitation.model.dto.NotificationDTO;
import com.supplyhouse.invitation.repository.InvitationRepository;
import com.supplyhouse.invitation.model.entity.Invitation;
import com.supplyhouse.invitation.model.enums.InvitationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private NotificationServiceClient notificationServiceClient;

    public String sendNewInvitation(Invitation invitation) {
        invitation.setInvitationToken(UUID.randomUUID().toString());
        invitation.setStatus(InvitationStatus.PENDING);
        Invitation savedInvitation = invitationRepository.save(invitation);

        NotificationDTO notificationDTO = NotificationDTO.builder()
                .senderId(String.valueOf(invitation.getBusinessOwnerId()))
                .recipientId(String.valueOf(invitation.getSubAccountId()))
                .senderEmail(invitation.getSenderEmail())
                .recipientEmail(invitation.getRecipientEmail())
                .build();
        notificationServiceClient.notifyRecipient(notificationDTO);
        return savedInvitation.getInvitationToken();
    }

    public String acceptInvitation(String token) {
        Invitation invitation = invitationRepository.findByInvitationToken(token)
                .orElseThrow(() -> new InvitationServiceException("Invitation to be accepted not found for token = "
                        + token));
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setAcceptedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        return "Invitation accepted!";
    }

    public String declineInvitation(String token) {
        Invitation invitation = invitationRepository.findByInvitationToken(token)
                .orElseThrow(() -> new InvitationServiceException("Invitation to be declined not found for token = "
                        + token));
        invitation.setStatus(InvitationStatus.DECLINED);
        invitation.setDeclinedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        return "Invitation declined!";
    }

    public List<Invitation> findAll() {
        return invitationRepository.findAll();
    }

    public Optional<Invitation> findByToken(String invitationToken) {
        return invitationRepository.findByInvitationToken(invitationToken);
    }
}

