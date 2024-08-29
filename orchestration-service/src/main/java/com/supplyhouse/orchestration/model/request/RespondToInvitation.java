package com.supplyhouse.orchestration.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondToInvitation {
    private String subAccountId;
    private String invitationToken;
    private boolean accept;
    private boolean shareFullHistory;
}
