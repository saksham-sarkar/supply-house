package com.supplyhouse.invitation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewInvitation {
    private String businessOwnerId;
    private String subAccountId;
}
