package com.supplyhouse.orchestration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespondToInvitationDTO {
    private  String subAccountId;
    private String invitationToken;
    private boolean isAccept;
    private boolean isShareFullHistory;
}
