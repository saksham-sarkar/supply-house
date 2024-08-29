package com.supplyhouse.account.service;

import com.supplyhouse.account.exception.AccountServiceException;
import com.supplyhouse.account.repository.AccountRepository;
import com.supplyhouse.account.model.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public String upgradeAccountToBusinessOwner(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountServiceException("Account requested to upgrade not found" +
                        " for accountId = " + accountId));
        account.setBusinessOwner(true);
        accountRepository.save(account);
        return "Account upgraded successfully";
    }

    public Boolean isSubaccountLinked(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountServiceException("SubAccount to be checked if linked " +
                        "not found for accountId = " + accountId));
        return account.getBusinessOwnerAccount() != null;
    }

    public String linkSubaccountToBusinessOwner(Long subaccountId, Long businessOwnerId, boolean shareFullHistory) {
        Account subAccount = accountRepository.findById(subaccountId)
                .orElseThrow(() -> new AccountServiceException("SubAccount to be linked not found " +
                        "for accountId = " + subaccountId));
        Account businessOwnerAccount =  accountRepository.findById(businessOwnerId)
                .orElseThrow(() -> new AccountServiceException("BusinessOwner account for linking not" +
                        " found for accountId = " + businessOwnerId));
        linkSubAccount(subAccount, businessOwnerAccount, shareFullHistory);
        accountRepository.save(subAccount);
        return "Account linked successfully to " + businessOwnerId;
    }

    public String unlinkSubaccount(Long accountId) {
        Account subAccount = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountServiceException("SubAccount to be unlinked not found " +
                        "for accountId = " + accountId));
        Account businessOwnerAccount = subAccount.getBusinessOwnerAccount();
        unlinkSubAccount(subAccount, businessOwnerAccount);
        return "Account unlinked successfully";
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountServiceException("Account not found for accountId = " + accountId));
    }

    public Account save(Account account) {
        if(account.isBusinessOwner()) {
            account.setSubAccounts(new ArrayList<>());
        }
        account.setBusinessOwnerAccount(null);
        return accountRepository.save(account);
    }

    // link a subaccount
    private void linkSubAccount(Account subAccount, Account businessOwnerAccount, boolean shareFullHistory) {
        subAccount.setBusinessOwnerAccount(businessOwnerAccount);
        subAccount.setAccountLinkDate(LocalDateTime.now());
        subAccount.setShareFullHistory(shareFullHistory);
        businessOwnerAccount.getSubAccounts().add(subAccount);
    }

    // unlink a subaccount
    private void unlinkSubAccount(Account subAccount, Account businessOwnerAccount) {
        businessOwnerAccount.getSubAccounts();
        businessOwnerAccount.getSubAccounts().remove(subAccount);
        subAccount.setBusinessOwnerAccount(null);
    }
}

