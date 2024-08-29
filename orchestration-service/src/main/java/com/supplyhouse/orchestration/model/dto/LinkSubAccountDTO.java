package com.supplyhouse.orchestration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkSubAccountDTO {
    private String subAccountId;
    private String businessOwnerId;
    private boolean shareFullHistory;
}
