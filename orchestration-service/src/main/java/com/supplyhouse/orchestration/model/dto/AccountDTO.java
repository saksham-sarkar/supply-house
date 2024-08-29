package com.supplyhouse.orchestration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private String accountId;
    private String email;
    private boolean isBusinessOwner;
    private String businessOwnerAccountId;
    private List<Long> subAccountIds = new ArrayList<>();
    private boolean isShareFullHistory;
    private String subAccountLinkedDate;
}
