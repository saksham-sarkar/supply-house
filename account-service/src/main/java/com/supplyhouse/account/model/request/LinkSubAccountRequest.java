package com.supplyhouse.account.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkSubAccountRequest {
    private String subAccountId;
    private String businessOwnerId;
    private boolean shareFullHistory;
}
