package com.supplyhouse.account.mapper;


import com.supplyhouse.account.model.dto.AccountDTO;
import com.supplyhouse.account.model.entity.Account;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static AccountDTO toAccountDTO(Account accountEntity) {
        if (accountEntity == null) {
            return null;
        }

        AccountDTO dto = new AccountDTO();
        dto.setAccountId(String.valueOf(accountEntity.getAccountId()));
        dto.setEmail(accountEntity.getEmail());
        dto.setBusinessOwner(accountEntity.isBusinessOwner());
        if(dto.isBusinessOwner()) {
            List<Long> subAccountIds = new ArrayList<>();
            List<Account> linkedSubAccounts = accountEntity.getSubAccounts();
            if(!linkedSubAccounts.isEmpty()) {
                for (Account subAccount : linkedSubAccounts) {
                    subAccountIds.add(subAccount.getAccountId());
                }
            }
            dto.setSubAccountIds(subAccountIds);
        } else {
            dto.setBusinessOwnerAccountId(accountEntity.getBusinessOwnerAccount() != null ?
                    String.valueOf(accountEntity.getBusinessOwnerAccount().getAccountId()) : null);
            dto.setShareFullHistory(accountEntity.isShareFullHistory());
            dto.setSubAccountLinkedDate(accountEntity.getAccountLinkDate()!=null?
                    accountEntity.getAccountLinkDate().format(FORMATTER):null);
        }

        return dto;
    }

    public static Account toAccount(AccountDTO dto) {
        if (dto == null) {
            return null;
        }

        return Account.builder()
                        .email(dto.getEmail())
                                .isBusinessOwner(dto.isBusinessOwner())
                                        .creationDate(LocalDateTime.now())
                                                .lastUpdated(LocalDateTime.now())
                .build();

    }
}
