package com.supplyhouse.orchestration.client;

import com.supplyhouse.orchestration.model.dto.AccountDTO;
import com.supplyhouse.orchestration.model.dto.LinkSubAccountDTO;
import com.supplyhouse.orchestration.model.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "${services.account-service.url}")
public interface AccountServiceClient {

    @PostMapping("/account/api/upgrade")
    String upgradeAccountToBusinessOwner(@RequestParam String accountId);

    @GetMapping("/account/api/isLinked/{accountId}")
    boolean isSubaccountLinked(@PathVariable String accountId);

    @GetMapping("/account/api/{accountId}")
    AccountDTO getAccount(@PathVariable String accountId);

    @PostMapping("/account/api/link-subaccount")
    String linkSubaccountToBusinessOwner(@RequestBody LinkSubAccountDTO request);

    @GetMapping("/account/api/unlink-subaccount/{accountId}")
    String unlinkSubaccount(@PathVariable String accountId);

    @PostMapping("/account/api/create")
    AccountDTO save(@RequestBody AccountDTO account);

}
