package com.supplyhouse.orchestration.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationRequest {
    private String businessOwnerId;
    private String subAccountId;
}
