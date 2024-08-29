package com.supplyhouse.invitation.service;

import com.supplyhouse.invitation.client.NotificationServiceClient;
import com.supplyhouse.invitation.model.dto.NotificationDTO;
import com.supplyhouse.invitation.repository.InvitationRepository;
import com.supplyhouse.invitation.model.entity.Invitation;
import com.supplyhouse.invitation.model.enums.InvitationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (savedInvitation==null) {
            throw new RuntimeException("Invitation creation failed.");
        }

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
        Invitation invitation = invitationRepository.findByInvitationToken(token).orElseThrow();
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitationRepository.save(invitation);
        return "Invitation accepted!";
    }

    public String declineInvitation(String token) {
        Invitation invitation = invitationRepository.findByInvitationToken(token).orElseThrow();
        invitation.setStatus(InvitationStatus.DECLINED);
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

