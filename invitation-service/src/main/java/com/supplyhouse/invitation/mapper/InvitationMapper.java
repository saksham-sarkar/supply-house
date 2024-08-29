package com.supplyhouse.invitation.mapper;



import com.supplyhouse.invitation.model.dto.InvitationDTO;
import com.supplyhouse.invitation.model.entity.Invitation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvitationMapper {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static InvitationDTO toInvitationDTO(Invitation invitationEntity) {
        if (invitationEntity == null) {
            return null;
        }

        return InvitationDTO.builder()
                .invitationId(String.valueOf(invitationEntity.getId()))
                        .invitationToken(invitationEntity.getInvitationToken())
                                .businessOwnerId(String.valueOf(invitationEntity.getBusinessOwnerId()))
                                        .subAccountId(String.valueOf(invitationEntity.getSubAccountId()))
                                                .senderEmail(invitationEntity.getSenderEmail())
                                                        .recipientEmail(invitationEntity.getRecipientEmail())
                                                                                .status(invitationEntity.getStatus()
                                                                                        .name())
                                                                                        .build();
    }

    public static Invitation toInvitation(InvitationDTO dto) {
        if (dto == null) {
            return null;
        }

        return Invitation.builder()
                .businessOwnerId(Long.parseLong(dto.getBusinessOwnerId()))
                .subAccountId(Long.parseLong(dto.getSubAccountId()))
                .senderEmail(dto.getSenderEmail())
                .recipientEmail(dto.getRecipientEmail())
                .sentAt(LocalDateTime.now())
                .build();

    }
}
