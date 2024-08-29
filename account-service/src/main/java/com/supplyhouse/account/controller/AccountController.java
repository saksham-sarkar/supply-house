package com.supplyhouse.account.controller;

import com.supplyhouse.account.model.dto.AccountDTO;
import com.supplyhouse.account.model.dto.LinkSubAccountDTO;
import com.supplyhouse.account.model.entity.Account;
import com.supplyhouse.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.supplyhouse.account.mapper.AccountMapper.toAccount;
import static com.supplyhouse.account.mapper.AccountMapper.toAccountDTO;

@RestController
@RequestMapping("/account/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/health")
    String health() {
        return "Runnning !";
    }

    @GetMapping("/{accountId}")
    AccountDTO getAccount(@PathVariable String accountId) {
        Account account = accountService.getAccount(Long.parseLong(accountId));
        return toAccountDTO(account);
    }

    @PostMapping("/create")
    AccountDTO save(@RequestBody AccountDTO dto){
        Account account = toAccount(dto);
        Account savedAccount = accountService.save(account);
        return toAccountDTO(savedAccount);
    }

    @PostMapping("/upgrade")
    public String upgradeToBusinessOwner(@RequestParam String accountId) {
        return accountService.upgradeAccountToBusinessOwner(Long.parseLong(accountId));
    }

    @PostMapping("/link-subaccount")
    public String linkSubaccountToBusinessOwner(@RequestBody LinkSubAccountDTO dto) {
        return accountService.linkSubaccountToBusinessOwner(Long.parseLong(dto.getSubAccountId()),
                Long.parseLong(dto.getBusinessOwnerId()), dto.isShareFullHistory());
    }

    @GetMapping("/unlink-subaccount/{accountId}")
    public String unlinkSubaccount(@PathVariable String accountId) {
        return accountService.unlinkSubaccount(Long.parseLong(accountId));
    }

    @GetMapping("/isLinked/{accountId}")
    public Boolean isSubaccountLinked(@PathVariable String accountId) {
        return accountService.isSubaccountLinked(Long.parseLong(accountId));
    }
}
